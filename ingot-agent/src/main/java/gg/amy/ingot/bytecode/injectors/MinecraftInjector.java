package gg.amy.ingot.bytecode.injectors;

import gg.amy.ingot.IngotAgent;
import gg.amy.ingot.api.Minecraft;
import gg.amy.ingot.bytecode.Injector;
import gg.amy.ingot.bytecode.mapping.MappedClass;
import gg.amy.ingot.test.Events;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

/**
 * @author amy
 * @since 5/16/21.
 */
public class MinecraftInjector extends Injector {
    private final MappedClass minecraft;

    public MinecraftInjector() {
        // TODO: Can we do better?
        super(IngotAgent.currentMappings().get("net.minecraft.client.Minecraft").obfuscatedName());
        minecraft = IngotAgent.currentMappings().get("net.minecraft.client.Minecraft");
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        // TODO: Fix this
        // To implement the interface, we can't then retransform this
        // But if we don't run retransform, the injector is never called
        cn.interfaces.add($(Minecraft.class));
        logger.info("Injected IngotMinecraft implementation");

        {
            final var node = new MethodNode(ACC_PUBLIC, "getVersion", "()" + $$(String.class), null, null);
            final var insns = new InsnList();
            insns.add(new VarInsnNode(ALOAD, 0));
            insns.add(new MethodInsnNode(INVOKEVIRTUAL, minecraft.obfuscatedName(), minecraft.methods().get("getLaunchedVersion()"), "()" + $$(String.class), false));
            insns.add(new InsnNode(ARETURN));
            node.instructions.clear();
            node.instructions.add(insns);
            cn.methods.add(node);
            logger.info("Injected getVersion implementation");
        }
        for(final var m : cn.methods) {
            // TODO: Store desc? How do we handle obf?
            if(m.name.equals(minecraft.methods().get("run()")) && m.desc.equals("()V")) {
                m.instructions.insert(new MethodInsnNode(INVOKESTATIC, $(Events.class), "gameStarted", "()V", false));
                logger.info("Injected Events#gameStarted()");
            }
        }
    }
}
