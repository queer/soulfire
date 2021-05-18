package gg.amy.soulfire.bytecode.redefiners;

import gg.amy.soulfire.api.minecraft.item.ItemProperties;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Redefiner;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

/**
 * @author amy
 * @since 5/17/21.
 */
public class ItemPropertiesRedefiner extends Redefiner {
    private final MappedClass itemProperties = ClassMap.lookup("net.minecraft.world.item.Item$Properties");

    public ItemPropertiesRedefiner() {
        super(ItemProperties.class.getName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var method : cn.methods) {
            if(method.name.equals("create")) {
                final var insns = new InsnList();
                insns.add(new TypeInsnNode(NEW, $(itemProperties.obfName())));
                insns.add(new InsnNode(DUP));
                insns.add(new MethodInsnNode(INVOKESPECIAL, $(itemProperties.obfName()), "<init>", itemProperties.method("<init>()").descNoComma(), false));
                insns.add(new InsnNode(ARETURN));

                method.instructions.clear();
                method.instructions.add(insns);
            }
        }
    }
}
