package gg.amy.soulfire.bytecode.synthesis;

import gg.amy.soulfire.annotations.*;
import gg.amy.soulfire.api.minecraft.Minecraft;
import gg.amy.soulfire.bytecode.*;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import gg.amy.soulfire.utils.AgentUtils;
import gg.amy.soulfire.utils.InsnPrinter;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author amy
 * @since 5/18/21.
 */
@SuppressWarnings("DuplicatedCode")
public final class BridgeAndShimSynthesiser implements Opcodes {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Set<Class<?>> PRIMITIVE_CLASSES = Set.of(
            boolean.class, byte.class, short.class, char.class, int.class, long.class,
            float.class, double.class
    );

    private BridgeAndShimSynthesiser() {
    }

    public static void synthesise(@Nonnull final Instrumentation i) throws UnmodifiableClassException, ClassNotFoundException {
        final var agents = AgentUtils.detectAgents();

        try(final var graph = new ClassGraph()
                .overrideClasspath((Object[]) agents.toArray(String[]::new))
                .acceptPackages(Minecraft.class.getPackageName())
                .enableAllInfo()
                .scan()) {
            final var dag = new DirectedAcyclicGraph<Class<?>, Edge>(Edge.class);

            for(final var ci : graph.getClassesWithAnnotation(Bridge.class.getName())) {
                final var bridgeClass = ci.loadClass();
                if(!bridgeClass.isInterface()) {
                    throw new IllegalStateException(String.format("@Bridge class %s isn't an interface!", bridgeClass.getName()));
                }

                dag.addVertex(bridgeClass);
                if(bridgeClass.isAnnotationPresent(TransformAfter.class)) {
                    final var after = bridgeClass.getDeclaredAnnotation(TransformAfter.class);
                    for(final Class<?> dependency : after.value()) {
                        dag.addVertex(dependency);
                        dag.addEdge(dependency, bridgeClass);
                    }
                }
            }

            for(final Class<?> bridgeClass : dag) {
                LOGGER.info("Operating on bridge class {}!", bridgeClass.getName());
                if(Arrays.stream(bridgeClass.getDeclaredMethods()).anyMatch(m -> m.isAnnotationPresent(DumpASM.class))) {
                    LOGGER.info("Bridging {}, will be dumping asm!", bridgeClass);
                }

                bridge(i, bridgeClass);
            }

            for(final var shim : graph.getClassesWithAnnotation(Shim.class.getName())) {
                if(shim.isInterface()) {
                    throw new IllegalArgumentException("@Shim class" + shim + " must not be an interface!");
                }
                if(shim.getInterfaces().isEmpty()) {
                    throw new IllegalArgumentException("@Shim class " + shim + " has no interfaces!");
                }

                shim(shim);
            }
        }
    }

    private static void bridge(@Nonnull final Instrumentation i, @Nonnull final Class<?> bridgeClass) throws UnmodifiableClassException, ClassNotFoundException {
        final var bridge = bridgeClass.getDeclaredAnnotation(Bridge.class);
        final var target = ClassMap.lookup(bridge.value());
        final var retransforming = bridgeClass.getDeclaredAnnotation(Retransforming.class) != null;

        LOGGER.debug("Registering bridge transformer.");

        // 1. Register a transformer to force the target to2
        //    implement the bridge interface.
        if(!bridgeClass.isAnnotationPresent(Nontransforming.class)) {
            addSyntheticBridgeInjector(i, target, bridgeClass, retransforming);
        }

        LOGGER.debug("Redefining bridge.");

        // 2. Redefine the bridge interface to implement static
        //    methods.
        generateSyntheticBridges(i, bridgeClass, target);
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
                        final var bridgeMethodName = m.getDeclaredAnnotation(BridgeMethod.class).value();
                        final var obfMethod = target.method(bridgeMethodName);

                        final var insns = new InsnList();
                        if(m.isAnnotationPresent(DumpASM.class)) {
                            logger.info("Generating bridge {} -> {}", bridgeMethodName, obfMethod.obfName());
                        }
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
                            insns.add(new MethodInsnNode(INVOKEINTERFACE, $(target.obfName()), obfMethod.obfName(), obfDesc, true));
                        } else {
                            insns.add(new MethodInsnNode(INVOKEVIRTUAL, $(target.obfName()), obfMethod.obfName(), obfDesc, false));
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
                            logger.info("Dumping method bridge asm for {}#{}", bridgeClass.getName(), m.getName());
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
                            logger.info("Dumping field bridge asm for {}#{}", bridgeClass.getName(), m.getName());
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
                        final var bridgeMethodName = m.getAnnotation(BridgeMethod.class).value();
                        final var obfMethod = target.method(bridgeMethodName);
                        final var reflDesc = Method.getMethod(m).getDescriptor();
                        // 2.2. Find matching MethodNode.
                        final var targetNode = cn.methods.stream().filter(mn -> mn.desc.equals(reflDesc)).findFirst();
                        if(targetNode.isEmpty()) {
                            throw new IllegalStateException("No node for method " + m.getName() + ' ' + reflDesc);
                        }
                        if(m.isAnnotationPresent(DumpASM.class)) {
                            logger.info("Generating bridge {} -> {}", bridgeMethodName, obfMethod.obfName());
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
                            logger.info("Dumping method bridge asm for {}#{}", bridgeClass.getName(), m.getName());
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
                            logger.info("Dumping field bridge asm for {}#{}", bridgeClass.getName(), m.getName());
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

    private static void shim(@Nonnull final ClassInfo shim) {
        LOGGER.info("Operating on shim class {}!", shim.getName());
        final var bridgeMethods = new HashSet<ShimmableMethod>();
        for(final ClassInfo ci : shim.getInterfaces()) {
            final var iface = ci.loadClass();
            if(iface.isAnnotationPresent(Bridge.class)) {
                bridgeMethods.addAll(detectShimmableMethods(iface));
                for(final Class<?> inner : iface.getInterfaces()) {
                    // TODO: Recursion
                    bridgeMethods.addAll(detectShimmableMethods(inner));
                }
            }
        }
        ClassDefiner.defineClass(ClassLoader.getSystemClassLoader(), new Generator(shim.getName()) {
            @Override
            protected void inject(final ClassReader cr, final ClassNode cn) {
                final var shimTarget = ClassMap.lookup((String) shim.getAnnotationInfo(Shim.class.getName()).getParameterValues().get(0).getValue());
                cn.superName = shimTarget.obfName();
                for(final var mn : cn.methods) {
                    if(mn.name.equals("<init>")) {
                        if(mn.visibleAnnotations != null) {
                            final var maybeAnnotation = mn.visibleAnnotations.stream().filter(an -> an.desc.equals($$(Constructor.class))).findFirst();
                            if(maybeAnnotation.isPresent()) {
                                final var ctor = (String) maybeAnnotation.get().values.get(1);
                                final var insns = new InsnList();
                                final var obfDesc = shimTarget.method(ctor).descNoComma();
                                final var obfTypes = Type.getArgumentTypes(obfDesc);

                                insns.add(new VarInsnNode(ALOAD, 0));
                                int argCounter = 0;
                                int stackPointer = 1;
                                for(final var type : Type.getArgumentTypes(mn.desc)) {
                                    switch(type.getDescriptor()) {
                                        case "D" -> {
                                            insns.add(new VarInsnNode(DLOAD, stackPointer));
                                            stackPointer += 2;
                                        }
                                        case "L" -> {
                                            insns.add(new VarInsnNode(LLOAD, stackPointer));
                                            stackPointer += 2;
                                        }
                                        default -> {
                                            final var insn = Type.getType(type.getDescriptor()).getOpcode(ILOAD);
                                            insns.add(new VarInsnNode(insn, stackPointer));
                                            if(insn == ALOAD) {
                                                insns.add(new TypeInsnNode(CHECKCAST, $(obfTypes[argCounter].getClassName())));
                                            }
                                            stackPointer += 1;
                                        }
                                    }
                                    argCounter += 1;
                                }
                                logger.warn("synthesising ctor {}", ctor);
                                insns.add(new MethodInsnNode(INVOKESPECIAL, cn.superName, "<init>", obfDesc, false));
                                insns.add(new InsnNode(RETURN));

                                if(mn.visibleAnnotations.stream().anyMatch(an -> an.desc.equals($$(DumpASM.class)))) {
                                    logger.info("Dumping method shim asm for {}#<init>", shim.getName());
                                    InsnPrinter.print(insns);
                                }
                                mn.instructions.clear();
                                mn.instructions.add(insns);
                            }
                        }
                    }
                }

                for(final ShimmableMethod shimmableMethod : bridgeMethods) {
                    if(shimmableMethod.ifaceMethod.getName().contains("<")) {
                        logger.warn("Skipping synthesising constructor-like method {}#{} for {}!", shimmableMethod.owner, shimmableMethod.ifaceMethod.getName(), shim.getName());
                    }

                    final var target = shimmableMethod.target;
                    final var m = shimmableMethod.ifaceMethod;
                    final var bridgeMethod = m.getAnnotation(BridgeMethod.class).value();
                    final var obfMethod = target.method(bridgeMethod);
                    final var obfDesc = obfMethod.descNoComma();

                    final var insns = new InsnList();

                    insns.add(new VarInsnNode(ALOAD, 0)); // this

                    int stackPointer = 1;
                    for(final Class<?> type : m.getParameterTypes()) {
                        final var loadInsn = Type.getType(type).getOpcode(ILOAD);
                        insns.add(new VarInsnNode(loadInsn, stackPointer));
                        if(!PRIMITIVE_CLASSES.contains(type)) {
                            insns.add(new TypeInsnNode(CHECKCAST, $(type.getName())));
                        }
                        if(type.equals(double.class) || type.equals(long.class)) {
                            stackPointer += 2;
                        } else {
                            stackPointer += 1;
                        }
                    }

                    // TODO: INVOKEINTERFACE?
                    insns.add(new MethodInsnNode(INVOKEVIRTUAL, $(shim.getName()), m.getName(), Method.getMethod(m).getDescriptor(), false));
                    if(!PRIMITIVE_CLASSES.contains(m.getReturnType())) {
                        insns.add(new TypeInsnNode(CHECKCAST, $(ClassMap.lookup(m.getReturnType().getAnnotation(Bridge.class).value()).obfName())));
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

                    // TODO: lol
                    if(shim.getMethodInfo(m.getName()).getSingleMethod(m.getName()).hasAnnotation(DumpASM.class.getName())) {
                        logger.info("Dumping method shim asm for {}#{} -> {}#{}", shim, obfMethod.obfName(), shim, m.getName());
                        InsnPrinter.print(insns);
                    }

                    final var mn = new MethodNode(ACC_PUBLIC, obfMethod.obfName(), obfMethod.descNoComma(), null, new String[0]);

                    mn.instructions.clear();
                    mn.instructions.add(insns);

                    cn.methods.add(mn);
                }
            }
        }.generate(), shim.getName());
    }

    private static List<ShimmableMethod> detectShimmableMethods(@Nonnull final Class<?> iface) {
        final var bridgeMethods = new ArrayList<ShimmableMethod>();
        final var bridge = ClassMap.lookup(iface.getAnnotation(Bridge.class).value());
        for(final var method : iface.getDeclaredMethods()) {
            if(method.isAnnotationPresent(BridgeMethod.class) && !Modifier.isStatic(method.getModifiers())) {
                bridgeMethods.add(new ShimmableMethod(bridge, iface, method));
            }
        }
        return bridgeMethods;
    }

    private record ShimmableMethod(MappedClass target, Class<?> owner, java.lang.reflect.Method ifaceMethod) {
        @Override
        public int hashCode() {
            return Objects.hash(target, owner, ifaceMethod);
        }
    }

    public static class Edge {

    }
}
