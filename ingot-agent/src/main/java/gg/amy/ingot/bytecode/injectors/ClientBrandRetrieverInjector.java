package gg.amy.ingot.bytecode.injectors;

import gg.amy.ingot.bytecode.Injector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;

/**
 * @author amy
 * @since 5/17/21.
 */
public class ClientBrandRetrieverInjector extends Injector {
    public ClientBrandRetrieverInjector() {
        super("net.minecraft.client.ClientBrandRetriever");
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var m : cn.methods) {
            if(m.name.equals("getClientModName")) {
                for(final AbstractInsnNode instruction : m.instructions) {
                    if(instruction instanceof LdcInsnNode ldc) {
                        ldc.cst = "ingot";
                    }
                }
                logger.info("Injected client mod name");
            }
        }
    }
}
