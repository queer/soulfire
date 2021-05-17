package gg.amy.ingot.bytecode.redefiners;

import gg.amy.ingot.api.ClientBrandRetriever;
import gg.amy.ingot.bytecode.Redefiner;
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
public class IngotClientBrandRetrieverRedefiner extends Redefiner {
    private final Logger logger = LogManager.getLogger();

    public IngotClientBrandRetrieverRedefiner() {
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
