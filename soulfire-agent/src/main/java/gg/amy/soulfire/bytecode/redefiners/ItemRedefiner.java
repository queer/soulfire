package gg.amy.soulfire.bytecode.redefiners;

import gg.amy.soulfire.api.minecraft.item.Item;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Redefiner;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

/**
 * @author amy
 * @since 5/18/21.
 */
public class ItemRedefiner extends Redefiner {
    private final MappedClass item = ClassMap.lookup("net.minecraft.world.item.Item");
    private final MappedClass itemProperties = ClassMap.lookup("net.minecraft.world.item.Item$Properties");

    public ItemRedefiner() {
        super(Item.class.getName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var method : cn.methods) {
            if(method.name.equals("create")) {
                final var insns = new InsnList();
                insns.add(new TypeInsnNode(NEW, $(item.obfName())));
                insns.add(new InsnNode(DUP));
                insns.add(new VarInsnNode(ALOAD, 0));
                insns.add(new TypeInsnNode(CHECKCAST, itemProperties.obfName()));
                insns.add(new MethodInsnNode(INVOKESPECIAL, $(item.obfName()), "<init>", item.method("<init>(net.minecraft.world.item.Item$Properties)").descNoComma(), false));
                insns.add(new InsnNode(ARETURN));

                method.instructions.clear();
                method.instructions.add(insns);
            }
        }
    }
}
