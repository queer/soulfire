package gg.amy.soulfire.bytecode.bridge;

import gg.amy.soulfire.annotations.*;
import gg.amy.soulfire.api.minecraft.Minecraft;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.Redefiner;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
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
import java.lang.management.ManagementFactory;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

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
        // Detect all attached agents
        final var runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        final var jvmArgs = runtimeMXBean.getInputArguments();
        final var agents = new ArrayList<String>();
        for(final var arg : jvmArgs) {
            if(arg.startsWith("-javaagent:")) {
                agents.add(arg.replace("-javaagent:", ""));
            }
        }

        try(final var graph = new ClassGraph()
                .overrideClasspath((Object[]) agents.toArray(String[]::new))
                .acceptPackages(Minecraft.class.getPackageName())
                .enableAllInfo()
                .scan()) {
            for(final var ci : graph.getClassesWithAnnotation(Bridge.class.getName())) {
                LOGGER.warn("Loading bridge class {}", ci.getName());
                final var bridgeClass = ci.loadClass();
                if(!bridgeClass.isInterface()) {
                    throw new IllegalStateException(String.format("@Bridge class %s isn't an interface!", bridgeClass.getName()));
                }
                LOGGER.info("Operating on bridge class {}!", bridgeClass.getName());
                final var bridge = bridgeClass.getDeclaredAnnotation(Bridge.class);
                final var target = ClassMap.lookup(bridge.value());
                final var retransforming = bridgeClass.getDeclaredAnnotation(Retransforming.class) != null;

                LOGGER.info("Registering bridge transformer.");

                // 1. Register a transformer to force the target to
                //    implement the bridge interface.
                if(!bridgeClass.isAnnotationPresent(Nontransforming.class)) {
                    addSyntheticBridgeInjector(i, target, bridgeClass, retransforming);
                }

                LOGGER.info("Redefining bridge.");

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
                cn.interfaces.add($(bridgeClass));

                for(final var m : bridgeClass.getDeclaredMethods()) {
                    // 1.1. Detect virtual bridge methods.
                    if(m.isAnnotationPresent(BridgeMethod.class) && !Modifier.isStatic(m.getModifiers())) {
                        logger.info("Synthesising non-static bridge {}#{}!", bridgeClass.getName(), m.getName());
                        final var obfMethod = target.method(m.getDeclaredAnnotation(BridgeMethod.class).value());
                        logger.warn("Targeting method: {}", m.getDeclaredAnnotation(BridgeMethod.class).value());

                        final var insns = new InsnList();
                        final var obfDesc = obfMethod.descNoComma();

                        // 1.2. Synthesise param-load insns.
                        insns.add(new VarInsnNode(ALOAD, 0)); // this

                        int counter = 1;
                        final var argTypes = obfMethod.argTypes();
                        for(final Class<?> type : m.getParameterTypes()) {
                            final var loadInsn = Type.getType(type).getOpcode(ILOAD);
                            insns.add(new VarInsnNode(loadInsn, counter));
                            if(argTypes.get(counter - 1).length() > 1) {
                                insns.add(new TypeInsnNode(CHECKCAST, argTypes.get(counter - 1)));
                            }
                            counter += 1;
                        }

                        insns.add(new MethodInsnNode(INVOKEVIRTUAL, target.obfName().replace('.', '/'), obfMethod.obfName(), obfDesc, false));

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

                        final var node = new MethodNode(ACC_PUBLIC, m.getName(), Method.getMethod(m).getDescriptor(), null, null);
                        LOGGER.warn("SYNTH:");
                        InsnPrinter.print(insns);
                        node.instructions.clear();
                        node.instructions.add(insns);
                        cn.methods.add(node);

                        logger.info("Bridging {}#{}.", target.obfName(), node.name);
                    }

                    // 1.4. Detect virtual field bridges.
                    if(m.isAnnotationPresent(BridgeField.class) && !Modifier.isStatic(m.getModifiers())) {
                        logger.info("Synthesising non-static field bridge {}#{}!", bridgeClass, m.getName());
                        final var obfField = target.field(m.getAnnotation(BridgeField.class).value());

                        final var insns = new InsnList();
                        final var obfDesc = obfField.type();

                        // 1.5. Synthesise load and return insns.
                        insns.add(new FieldInsnNode(GETFIELD, target.obfName().replace('.', '/'), obfField.name(), obfDesc));

                        insns.add(new InsnNode(switch(obfDesc.charAt(obfDesc.length() - 1)) {
                            case 'Z', 'I', 'C', 'S', 'B' -> IRETURN;
                            case 'J' -> LRETURN;
                            case 'F' -> FRETURN;
                            case 'D' -> DRETURN;
                            case ';' -> ARETURN;
                            default -> ARETURN;
                        }));

                        final var node = new MethodNode(ACC_PUBLIC, m.getName(), Method.getMethod(m).getDescriptor(), null, null);
                        node.instructions.clear();
                        node.instructions.add(insns);
                        cn.methods.add(node);

                        logger.info("Bridging {}#{}.", target.obfName(), node.name);
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
                        // TODO: Allow bridging statics to constructors for factory methods
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
                        logger.info("Redefining {}#{}...", bridgeClass.getName(), mn.name);

                        if(obfMethod.obfName().startsWith("<init>")) {
                            insns.add(new TypeInsnNode(NEW, target.obfName()));
                            insns.add(new InsnNode(DUP));
                        }

                        int counter = 0;
                        final var argTypes = obfMethod.argTypes();
                        for(final Class<?> type : m.getParameterTypes()) {
                            final var loadInsn = Type.getType(type).getOpcode(ILOAD);
                            insns.add(new VarInsnNode(loadInsn, counter));
                            if(argTypes.get(counter).length() > 1) {
                                insns.add(new TypeInsnNode(CHECKCAST, argTypes.get(counter)));
                            }
                            counter += 1;
                        }

                        if(obfMethod.obfName().startsWith("<init>")) {
                            insns.add(new MethodInsnNode(INVOKESPECIAL, $(target.obfName()), obfMethod.obfName(), obfDesc, false));
                            insns.add(new TypeInsnNode(CHECKCAST, $(m.getReturnType().getName())));
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

                        mn.instructions.clear();
                        mn.instructions.add(insns);
                        InsnPrinter.print(mn.instructions);
                        mn.maxLocals = m.getParameterCount() * 2;
                        mn.maxStack = m.getParameterCount() * 2;
                        logger.info("Redefined {}#{}.", bridgeClass.getName(), mn.name);
                    }

                    // 2.4. Detect static field bridges.
                    if(m.isAnnotationPresent(BridgeField.class) && Modifier.isStatic(m.getModifiers())) {
                        logger.info("Synthesising non-static field bridge {}#{}!", bridgeClass, m.getName());
                        final var obfField = target.field(m.getAnnotation(BridgeField.class).value());
                        final var reflDesc = Method.getMethod(m).getDescriptor();
                        final var targetNode = cn.methods.stream().filter(mn -> mn.name.equals(m.getName()) && mn.desc.equals(reflDesc)).findFirst();
                        if(targetNode.isEmpty()) {
                            throw new IllegalStateException("No node for method " + m.getName() + ' ' + reflDesc);
                        }

                        final var mn = targetNode.get();
                        final var insns = new InsnList();
                        LOGGER.warn("FETCHING FIELD: {}", m.getAnnotation(BridgeField.class).value());
                        final var obfDesc = obfField.desc();

                        // 2.5. Synthesise load and return insns.
                        insns.add(new FieldInsnNode(GETSTATIC, target.obfName().replace('.', '/'), obfField.obfName(), obfDesc));

                        insns.add(new InsnNode(switch(obfDesc.charAt(obfDesc.length() - 1)) {
                            case 'Z', 'I', 'C', 'S', 'B' -> IRETURN;
                            case 'J' -> LRETURN;
                            case 'F' -> FRETURN;
                            case 'D' -> DRETURN;
                            case ';' -> ARETURN;
                            default -> ARETURN;
                        }));

                        mn.instructions.clear();
                        mn.instructions.add(insns);

                        logger.info("Bridging {}#{}.", target.obfName(), mn.name);
                    }
                }
                logger.info("Fully redefined {}.", bridgeClass.getName());
            }
        }.redefine());
    }
}
