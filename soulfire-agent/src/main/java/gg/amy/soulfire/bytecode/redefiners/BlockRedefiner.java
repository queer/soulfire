package gg.amy.soulfire.bytecode.redefiners;

import gg.amy.soulfire.api.minecraft.block.Block;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Redefiner;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

/**
 * @author amy
 * @since 5/18/21.
 */
public class BlockRedefiner extends Redefiner {
    private final MappedClass block = ClassMap.lookup("net.minecraft.world.level.block.Block");
    private final MappedClass blockProperties = ClassMap.lookup("net.minecraft.world.level.block.state.BlockBehaviour$Properties");

    public BlockRedefiner() {
        super(Block.class.getName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var method : cn.methods) {
            if(method.name.equals("create")) {
                final var insns = new InsnList();
                insns.add(new TypeInsnNode(NEW, $(block.obfName())));
                insns.add(new InsnNode(DUP));
                insns.add(new VarInsnNode(ALOAD, 0));
                insns.add(new TypeInsnNode(CHECKCAST, $(blockProperties.obfName())));
                insns.add(new MethodInsnNode(INVOKESPECIAL, $(block.obfName()), "<init>", block.method("<init>(net.minecraft.world.level.block.state.BlockBehaviour$Properties)").descNoComma(), false));
                insns.add(new InsnNode(ARETURN));

                method.instructions.clear();
                method.instructions.add(insns);
            }
        }
    }
}
