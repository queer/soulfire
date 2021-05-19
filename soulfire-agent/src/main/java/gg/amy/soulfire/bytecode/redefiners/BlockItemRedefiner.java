package gg.amy.soulfire.bytecode.redefiners;

import gg.amy.soulfire.api.minecraft.item.BlockItem;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Redefiner;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

/**
 * @author amy
 * @since 5/18/21.
 */
public class BlockItemRedefiner extends Redefiner {
    private final MappedClass blockItem = ClassMap.lookup("net.minecraft.world.item.BlockItem");
    private final MappedClass block = ClassMap.lookup("net.minecraft.world.level.block.Block");
    private final MappedClass itemProperties = ClassMap.lookup("net.minecraft.world.item.Item$Properties");

    public BlockItemRedefiner() {
        super(BlockItem.class.getName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var method : cn.methods) {
            if(method.name.equals("create")) {
                final var insns = new InsnList();
                insns.add(new TypeInsnNode(NEW, $(blockItem.obfName())));
                insns.add(new InsnNode(DUP));
                insns.add(new VarInsnNode(ALOAD, 0));
                insns.add(new TypeInsnNode(CHECKCAST, $(block.obfName())));
                insns.add(new VarInsnNode(ALOAD, 1));
                insns.add(new TypeInsnNode(CHECKCAST, $(itemProperties.obfName())));
                insns.add(new MethodInsnNode(INVOKESPECIAL, $(blockItem.obfName()), "<init>", blockItem.method("<init>(net.minecraft.world.level.block.Block,net.minecraft.world.item.Item$Properties)").descNoComma(), false));
                insns.add(new InsnNode(ARETURN));

                method.instructions.clear();
                method.instructions.add(insns);
            }
        }
    }
}
