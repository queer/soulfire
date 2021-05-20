package gg.amy.soulfire.bytecode.synthesis;

import gg.amy.soulfire.annotations.*;
import gg.amy.soulfire.api.minecraft.Minecraft;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.Redefiner;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import gg.amy.soulfire.utils.AgentUtils;
import gg.amy.soulfire.utils.InsnPrinter;
import io.github.classgraph.ClassGraph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Modifier;

/**
 * @author amy
 * @since 5/18/21.
 */
@SuppressWarnings("DuplicatedCode")
public final class BridgeSynthesiser implements Opcodes {
    private static final Logger LOGGER = LogManager.getLogger();

    private BridgeSynthesiser() {
    }

    public static void synthesise(@Nonnull final Instrumentation i) throws UnmodifiableClassException, ClassNotFoundException {
        final var agents = AgentUtils.detectAgents();

        try(final var graph = new ClassGraph()
                .overrideClasspath((Object[]) agents.toArray(String[]::new))
                .acceptPackages(Minecraft.class.getPackageName())
                .enableAllInfo()
                .scan()) {
            for(final var ci : graph.getClassesWithAnnotation(Bridge.class.getName())) {
                final var bridgeClass = ci.loadClass();
                if(!bridgeClass.isInterface()) {
                    throw new IllegalStateException(String.format("@Bridge class %s isn't an interface!", bridgeClass.getName()));
                }
                LOGGER.debug("Operating on bridge class {}!", bridgeClass.getName());
                final var bridge = bridgeClass.getDeclaredAnnotation(Bridge.class);
                final var target = ClassMap.lookup(bridge.value());
                final var retransforming = bridgeClass.getDeclaredAnnotation(Retransforming.class) != null;

                LOGGER.debug("Registering bridge transformer.");

                // 1. Register a transformer to force the target to
                //    implement the bridge interface.
                if(!bridgeClass.isAnnotationPresent(Nontransforming.class)) {
                    addSyntheticBridgeInjector(i, target, bridgeClass, retransforming);
                }

                LOGGER.debug("Redefining bridge.");

                // 2. Redefine the bridge interface to implement static
                //    methods.
                generateSyntheticBridges(i, bridgeClass, target);
            }
        }
    }

    private static void addSyntheticBridgeInjector(@Nonnull final Instrumentation i, @Nonnull final MappedClass target,
                                                   @Nonnull final Class<?> bridgeClass, final boolean retransforming)
            throws ClassNotFoundException, UnmodifiableClassException {
        i.addTransformer(new Injector(target.obfName()) {
            @Override
            protected void inject(final ClassReader cr, final ClassNode cn) {
                if(cn.interfaces.contains($(bridgeClass))) {
                    logger.warn("Class {} already has interface {}", target.name(), bridgeClass);
                } else {
                    cn.interfaces.add($(bridgeClass));
                }

                for(final var m : bridgeClass.getDeclaredMethods()) {
                    // 1.1. Detect virtual bridge methods.
                    if(m.isAnnotationPresent(BridgeMethod.class) && !Modifier.isStatic(m.getModifiers())) {
                        logger.debug("Synthesising non-static bridge {}#{}!", bridgeClass.getName(), m.getName());
                        final var obfMethod = target.method(m.getDeclaredAnnotation(BridgeMethod.class).value());

                        final var insns = new InsnList();
                        final var obfDesc = obfMethod.descNoComma();

                        // 1.2. Synthesise param-load insns.
                        insns.add(new VarInsnNode(ALOAD, 0)); // this

                        // We track a separate stack pointer because longs and
                        // doubles take up two slots in locals and stack, but
                        // we still need to load param types normally.
                        int argPointer = 0;
                        int stackPointer = 1;
                        final var argTypes = obfMethod.argTypes();
                        for(final Class<?> type : m.getParameterTypes()) {
                            final var loadInsn = Type.getType(type).getOpcode(ILOAD);
                            insns.add(new VarInsnNode(loadInsn, stackPointer));
                            if(argTypes.get(argPointer).length() > 1) {
                                insns.add(new TypeInsnNode(CHECKCAST, argTypes.get(argPointer)));
                            }
                            argPointer += 1;
                            if(type.equals(double.class) || type.equals(long.class)) {
                                stackPointer += 2;
                            } else {
                                stackPointer += 1;
                            }
                        }

                        if(m.isAnnotationPresent(Interface.class)) {
                            insns.add(new MethodInsnNode(INVOKEINTERFACE, target.obfName().replace('.', '/'), obfMethod.obfName(), obfDesc, true));
                        } else {
                            insns.add(new MethodInsnNode(INVOKEVIRTUAL, target.obfName().replace('.', '/'), obfMethod.obfName(), obfDesc, false));
                        }

                        // 1.3. Synthesise correct return insn.
                        insns.add(new InsnNode(switch(obfDesc.charAt(obfDesc.length() - 1)) {
                            case 'Z', 'I', 'C', 'S', 'B' -> IRETURN;
                            case 'J' -> LRETURN;
                            case 'F' -> FRETURN;
                            case 'D' -> DRETURN;
                            case 'V' -> RETURN;
                            case ';' -> ARETURN;
                            default -> throw new IllegalStateException("Unexpected value: " + obfDesc.charAt(obfDesc.length() - 1));
                        }));

                        if(m.isAnnotationPresent(DumpASM.class)) {
                            InsnPrinter.print(insns);
                        }

                        final var node = new MethodNode(ACC_PUBLIC, m.getName(), Method.getMethod(m).getDescriptor(), null, null);
                        node.instructions.clear();
                        node.instructions.add(insns);
                        cn.methods.add(node);

                        logger.debug("Bridging {}#{}.", target.obfName(), node.name);
                    }

                    // 1.4. Detect virtual field bridges.
                    if(m.isAnnotationPresent(BridgeField.class) && !Modifier.isStatic(m.getModifiers())) {
                        // TODO: Support setters
                        logger.debug("Synthesising non-static field bridge {}#{}!", bridgeClass, m.getName());
                        final var obfField = target.field(m.getAnnotation(BridgeField.class).value());

                        final var insns = new InsnList();
                        final var obfDesc = obfField.desc();

                        // 1.5. Synthesise load and return insns.
                        insns.add(new VarInsnNode(ALOAD, 0));
                        insns.add(new FieldInsnNode(GETFIELD, $(target.obfName()), obfField.obfName(), obfDesc));

                        insns.add(new InsnNode(switch(obfDesc.charAt(obfDesc.length() - 1)) {
                            case 'Z', 'I', 'C', 'S', 'B' -> IRETURN;
                            case 'J' -> LRETURN;
                            case 'F' -> FRETURN;
                            case 'D' -> DRETURN;
                            case ';' -> ARETURN;
                            default -> ARETURN;
                        }));

                        if(m.isAnnotationPresent(DumpASM.class)) {
                            InsnPrinter.print(insns);
                        }

                        final var node = new MethodNode(ACC_PUBLIC, m.getName(), Method.getMethod(m).getDescriptor(), null, null);
                        node.instructions.clear();
                        node.instructions.add(insns);
                        cn.methods.add(node);

                        logger.debug("Bridging {}#{}.", target.obfName(), node.name);
                    }
                }
            }
        }, retransforming);
        if(retransforming) {
            i.retransformClasses(Class.forName(target.obfName()));
        }
    }

    private static void generateSyntheticBridges(@Nonnull final Instrumentation i, @Nonnull final Class<?> bridgeClass,
                                                 @Nonnull final MappedClass target)
            throws UnmodifiableClassException, ClassNotFoundException {
        i.redefineClasses(new Redefiner(bridgeClass.getName()) {
            @Override
            protected void inject(final ClassReader cr, final ClassNode cn) {
                for(final var m : bridgeClass.getDeclaredMethods()) {
                    // 2.1. Detect static bridge methods
                    if(m.isAnnotationPresent(BridgeMethod.class) && Modifier.isStatic(m.getModifiers())) {
                        final var obfMethod = target.method(m.getAnnotation(BridgeMethod.class).value());
                        final var reflDesc = Method.getMethod(m).getDescriptor();
                        // 2.2. Find matching MethodNode.
                        final var targetNode = cn.methods.stream().filter(mn -> mn.desc.equals(reflDesc)).findFirst();
                        if(targetNode.isEmpty()) {
                            throw new IllegalStateException("No node for method " + m.getName() + ' ' + reflDesc);
                        }

                        final var mn = targetNode.get();
                        final var insns = new InsnList();
                        final var obfDesc = obfMethod.descNoComma();
                        logger.debug("Redefining {}#{}...", bridgeClass.getName(), mn.name);

                        if(obfMethod.obfName().startsWith("<init>")) {
                            insns.add(new TypeInsnNode(NEW, target.obfName()));
                            insns.add(new InsnNode(DUP));
                        }

                        // We track a separate stack pointer because longs and
                        // doubles take up two slots in locals and stack, but
                        // we still need to load param types normally.
                        int argPointer = 0;
                        int stackPointer = 0;
                        final var argTypes = obfMethod.argTypes();
                        for(final Class<?> type : m.getParameterTypes()) {
                            final var loadInsn = Type.getType(type).getOpcode(ILOAD);
                            insns.add(new VarInsnNode(loadInsn, stackPointer));
                            if(argTypes.get(argPointer).length() > 1) {
                                insns.add(new TypeInsnNode(CHECKCAST, argTypes.get(argPointer)));
                            }
                            argPointer += 1;
                            if(type.equals(double.class) || type.equals(long.class)) {
                                stackPointer += 2;
                            } else {
                                stackPointer += 1;
                            }
                        }

                        if(obfMethod.obfName().startsWith("<init>")) {
                            insns.add(new MethodInsnNode(INVOKESPECIAL, $(target.obfName()), obfMethod.obfName(), obfDesc, false));
//                            insns.add(new TypeInsnNode(CHECKCAST, $(m.getReturnType().getName())));
                            insns.add(new InsnNode(ARETURN));
                        } else {
                            insns.add(new MethodInsnNode(INVOKESTATIC, $(target.obfName()), obfMethod.obfName(), obfDesc, false));
                            // 2.3. Synthesise correct return insn.
                            insns.add(new InsnNode(switch(obfDesc.charAt(obfDesc.length() - 1)) {
                                case 'Z', 'I', 'C', 'S', 'B' -> IRETURN;
                                case 'J' -> LRETURN;
                                case 'F' -> FRETURN;
                                case 'D' -> DRETURN;
                                case 'V' -> RETURN;
                                case ';' -> ARETURN;
                                default -> throw new IllegalStateException("Unexpected value: " + obfDesc.charAt(obfDesc.length() - 1));
                            }));
                        }

                        if(m.isAnnotationPresent(DumpASM.class)) {
                            InsnPrinter.print(insns);
                        }

                        mn.instructions.clear();
                        mn.instructions.add(insns);
                        mn.maxLocals = m.getParameterCount() * 2;
                        mn.maxStack = m.getParameterCount() * 2;
                        logger.debug("Redefined {}#{}.", bridgeClass.getName(), mn.name);
                    }

                    // 2.4. Detect static field bridges.
                    if(m.isAnnotationPresent(BridgeField.class) && Modifier.isStatic(m.getModifiers())) {
                        logger.debug("Synthesising non-static field bridge {}#{}!", bridgeClass, m.getName());
                        final var obfField = target.field(m.getAnnotation(BridgeField.class).value());
                        final var reflDesc = Method.getMethod(m).getDescriptor();
                        final var targetNode = cn.methods.stream().filter(mn -> mn.name.equals(m.getName()) && mn.desc.equals(reflDesc)).findFirst();
                        if(targetNode.isEmpty()) {
                            throw new IllegalStateException("No node for method " + m.getName() + ' ' + reflDesc);
                        }

                        final var mn = targetNode.get();
                        final var insns = new InsnList();
                        final var obfDesc = obfField.desc();

                        // 2.5. Synthesise insns.
                        if(m.isAnnotationPresent(Setter.class)) {
                            insns.add(new VarInsnNode(Type.getType(m.getParameterTypes()[0]).getOpcode(ILOAD), 0));
                            insns.add(new FieldInsnNode(PUTSTATIC, $(target.obfName()), obfField.obfName(), obfDesc));
                            insns.add(new InsnNode(RETURN));
                        } else {
                            insns.add(new FieldInsnNode(GETSTATIC, $(target.obfName()), obfField.obfName(), obfDesc));
                            insns.add(new InsnNode(switch(obfDesc.charAt(obfDesc.length() - 1)) {
                                case 'Z', 'I', 'C', 'S', 'B' -> IRETURN;
                                case 'J' -> LRETURN;
                                case 'F' -> FRETURN;
                                case 'D' -> DRETURN;
                                case ';' -> ARETURN;
                                default -> ARETURN;
                            }));
                        }

                        if(m.isAnnotationPresent(DumpASM.class)) {
                            InsnPrinter.print(insns);
                        }

                        mn.instructions.clear();
                        mn.instructions.add(insns);

                        logger.debug("Bridging {}#{}.", target.obfName(), mn.name);
                    }
                }
                logger.debug("Fully redefined {}.", bridgeClass.getName());
            }
        }.redefine());
    }
}
