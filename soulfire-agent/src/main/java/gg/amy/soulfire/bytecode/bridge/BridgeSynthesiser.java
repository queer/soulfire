package gg.amy.soulfire.bytecode.bridge;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeMethod;
import gg.amy.soulfire.annotations.Retransforming;
import gg.amy.soulfire.api.minecraft.Minecraft;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.Redefiner;
import io.github.classgraph.ClassGraph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
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
                final var bridgeClass = ci.loadClass();
                if(!bridgeClass.isInterface()) {
                    throw new IllegalStateException(String.format("@Bridge class %s isn't an interface!", bridgeClass.getName()));
                }
                LOGGER.info("Operating on bridge class {}!", bridgeClass.getName());
                final var bridge = bridgeClass.getDeclaredAnnotation(Bridge.class);
                final var target = ClassMap.lookup(bridge.value());

                // 1. Register a transformer to force the target to
                //    implement the bridge interface.
                i.addTransformer(new Injector(target.obfName()) {
                    @Override
                    protected void inject(final ClassReader cr, final ClassNode cn) {
                        cn.interfaces.add($(bridgeClass));

                        // 1.1. Detect virtual bridge methods.
                        for(final var m : bridgeClass.getMethods()) {
                            if(m.isAnnotationPresent(BridgeMethod.class) && !Modifier.isStatic(m.getModifiers())) {
                                logger.info("Synthesising non-static bridge {}#{}!", bridgeClass.getName(), m.getName());
                                final var obfMethod = target.method(m.getAnnotation(BridgeMethod.class).value());

                                final var insns = new InsnList();
                                final var obfDesc = obfMethod.desc();

                                // 1.2. Synthesise param-load insns.
                                insns.add(new VarInsnNode(ALOAD, 0)); // this

                                int counter = 1;
                                for(final Class<?> type : m.getParameterTypes()) {
                                    final int loadInsn;
                                    if(type.equals(boolean.class)) {
                                        loadInsn = ILOAD;
                                    } else if(type.equals(byte.class)) {
                                        loadInsn = BIPUSH;
                                    } else if(type.equals(short.class)) {
                                        loadInsn = SIPUSH;
                                    } else if(type.equals(char.class)) {
                                        loadInsn = SIPUSH;
                                    } else if(type.equals(int.class)) {
                                        loadInsn = ILOAD;
                                    } else if(type.equals(long.class)) {
                                        loadInsn = LLOAD;
                                    } else if(type.equals(float.class)) {
                                        loadInsn = FLOAD;
                                    } else if(type.equals(double.class)) {
                                        loadInsn = DLOAD;
                                    } else {
                                        loadInsn = ALOAD;
                                    }

                                    insns.add(new VarInsnNode(loadInsn, counter));
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
                                node.instructions.clear();
                                node.instructions.add(insns);
                                cn.methods.add(node);

                                logger.info("Bridging {}#{}.", target.obfName(), node.name);
                            }
                        }
                    }
                }, bridgeClass.isAnnotationPresent(Retransforming.class));

                // 2. Redefine the bridge interface to implement static
                //    methods.
                i.redefineClasses(new Redefiner(bridgeClass.getName()) {
                    @Override
                    protected void inject(final ClassReader cr, final ClassNode cn) {
                        // 2.1. Detect static bridge methods
                        for(final var m : bridgeClass.getMethods()) {
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
                                final var obfDesc = obfMethod.desc();

                                insns.add(new MethodInsnNode(INVOKESTATIC, target.obfName().replace('.', '/'), obfMethod.obfName(), obfDesc, false));
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

                                mn.instructions.clear();
                                mn.instructions.add(insns);
                                logger.info("Redefined {}#{}.", bridgeClass.getName(), mn.name);
                            }
                        }
                        logger.info("Fully redefined {}.", bridgeClass.getName());
                    }
                }.redefine());
            }
        }
    }
}
