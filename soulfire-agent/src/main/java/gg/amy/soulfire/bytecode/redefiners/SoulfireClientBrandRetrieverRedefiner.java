package gg.amy.soulfire.bytecode.redefiners;

import gg.amy.soulfire.api.minecraft.ClientBrandRetriever;
import gg.amy.soulfire.bytecode.Redefiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

/**
 * @author amy
 * @since 5/17/21.
 */
public class SoulfireClientBrandRetrieverRedefiner extends Redefiner {
    private final Logger logger = LogManager.getLogger();

    public SoulfireClientBrandRetrieverRedefiner() {
        super(ClientBrandRetriever.class.getName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var method : cn.methods) {
            if("getClientModName".equals(method.name)) {
                final var insns = new InsnList();
                insns.add(new MethodInsnNode(INVOKESTATIC, "net/minecraft/client/ClientBrandRetriever", "getClientModName", "()Ljava/lang/String;", false));
                insns.add(new InsnNode(ARETURN));

                method.instructions.clear();
                method.instructions.add(insns);
                logger.info("Redefined ClientBrandRetriever");
            }
        }
    }
}
