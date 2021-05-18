package gg.amy.soulfire.bytecode.injectors;

import gg.amy.soulfire.api.minecraft.Minecraft;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import gg.amy.soulfire.test.Events;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

/**
 * @author amy
 * @since 5/16/21.
 */
public class MinecraftInjector extends Injector {
    private final MappedClass minecraft = ClassMap.lookup("net.minecraft.client.Minecraft");

    public MinecraftInjector() {
        super(ClassMap.lookup("net.minecraft.client.Minecraft").obfName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        // Add interface
        cn.interfaces.add($(Minecraft.class));

        // Define interface method implementations
        {
            final var node = new MethodNode(ACC_PUBLIC, "getLaunchedVersion", "()" + $$(String.class), null, null);
            final var insns = new InsnList();
            insns.add(new VarInsnNode(ALOAD, 0));
            insns.add(new MethodInsnNode(INVOKEVIRTUAL, minecraft.obfName(), minecraft.method("getLaunchedVersion()").obfName(), "()" + $$(String.class), false));
            insns.add(new InsnNode(ARETURN));
            node.instructions.clear();
            node.instructions.add(insns);
            cn.methods.add(node);
            logger.info("Injected getLaunchedVersion implementation");
        }

        // Inject events
        final var run = minecraft.methods().get("run()");
        for(final var m : cn.methods) {
            if(m.name.equals(run.obfName()) && m.desc.equals(run.desc())) {
                m.instructions.insert(new MethodInsnNode(INVOKESTATIC, $(Events.class), "gameStarted", "()V", false));
                logger.info("Injected Events#gameStarted()");
            }
        }
        logger.info("Injected SoulfireMinecraft implementation");
    }
}
