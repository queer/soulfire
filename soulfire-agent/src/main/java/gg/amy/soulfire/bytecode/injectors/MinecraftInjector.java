package gg.amy.soulfire.bytecode.injectors;

import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import gg.amy.soulfire.test.Events;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;

/**
 * @author amy
 * @since 5/16/21.
 */
public class MinecraftInjector extends Injector {
    private final MappedClass minecraft = ClassMap.lookup("net.minecraft.client.Minecraft");

    public MinecraftInjector() {
        super(ClassMap.lookup("net.minecraft.client.Minecraft").obfName(), true);
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
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
