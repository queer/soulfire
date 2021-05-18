package gg.amy.soulfire.bytecode.redefiners;

import gg.amy.soulfire.api.minecraft.Minecraft;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Redefiner;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
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
public class SoulfireMinecraftRedefiner extends Redefiner {
    private final Logger logger = LogManager.getLogger();
    private final MappedClass minecraft;

    public SoulfireMinecraftRedefiner() {
        super(Minecraft.class.getName());
        minecraft = ClassMap.lookup("net.minecraft.client.Minecraft");
    }


    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var method : cn.methods) {
            switch(method.name) {
                case "getInstance": {
                    final var getInstance = minecraft.methods().get("getInstance()");
                    final var insns = new InsnList();
                    insns.add(new MethodInsnNode(INVOKESTATIC, minecraft.obfName(), getInstance.obfName(), getInstance.desc(), false));
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
