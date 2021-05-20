package gg.amy.soulfire.bytecode.redefiners;

import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.api.SoulfireImpl;
import gg.amy.soulfire.bytecode.Redefiner;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;

/**
 * @author amy
 * @since 5/19/21.
 */
public class SoulfireRedefiner extends Redefiner {
    public SoulfireRedefiner() {
        super(Soulfire.class.getName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var method : cn.methods) {
            if(method.name.equals("soulfire")) {
                final var insns = new InsnList();
                insns.add(new FieldInsnNode(GETSTATIC, $(SoulfireImpl.class), "INSTANCE", $$(Soulfire.class)));
                insns.add(new InsnNode(ARETURN));

                method.instructions.clear();
                method.instructions.add(insns);
            }
        }
    }
}
