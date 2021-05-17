package gg.amy.ingot.bytecode.redefiners;

import gg.amy.ingot.api.Minecraft;
import gg.amy.ingot.bytecode.ClassMap;
import gg.amy.ingot.bytecode.Redefiner;
import gg.amy.ingot.bytecode.mapping.MappedClass;
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
public class IngotMinecraftRedefiner extends Redefiner {
    private final Logger logger = LogManager.getLogger();
    private final MappedClass minecraft;

    public IngotMinecraftRedefiner() {
        super(Minecraft.class.getName());
        minecraft = ClassMap.lookup("net.minecraft.client.Minecraft");
    }


    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var method : cn.methods) {
            switch(method.name) {
                case "getInstance": {
                    final var insns = new InsnList();
                    insns.add(new MethodInsnNode(INVOKESTATIC, minecraft.obfuscatedName(), minecraft.methods().get("getInstance()"), "()" + minecraft.descriptor(), false));
                    insns.add(new InsnNode(ARETURN));

                    method.instructions.clear();
                    method.instructions.add(insns);
                    logger.info("Redefined Minecraft#getInstance()");
                    break;
                }
            }
        }
    }
}
