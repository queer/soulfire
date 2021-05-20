package gg.amy.soulfire.bytecode.injectors;

import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import gg.amy.soulfire.events.Hooks;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

/**
 * @author amy
 * @since 5/20/21.
 */
public class CreativeModeInventoryScreenInjector extends Injector {
    private MappedClass gui = ClassMap.lookup("net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen");

    public CreativeModeInventoryScreenInjector() {
        super(ClassMap.lookup("net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen").obfName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        final var editBox = ClassMap.lookup("net.minecraft.client.gui.components.EditBox");
        final var getValue = editBox.method("getValue()");
        final var refresh = gui.method("refreshSearchResults()");
        final var searchBox = gui.field("searchBox");
        for(final var mn : cn.methods) {
            if(mn.name.equals(refresh.obfName()) && mn.desc.equals(refresh.descNoComma())) {
                final var insns = new InsnList();

                insns.add(new VarInsnNode(ALOAD, 0));
                insns.add(new FieldInsnNode(GETFIELD, $(gui.obfName()), searchBox.obfName(), editBox.descriptor()));
                insns.add(new MethodInsnNode(INVOKEVIRTUAL, $(editBox.obfName()), getValue.obfName(), getValue.descNoComma(), false));
                insns.add(new MethodInsnNode(INVOKESTATIC, $(Hooks.class), "fireCreativeSearchUpdate", String.format("(%s)V", $$(String.class)), false));

                mn.instructions.insert(insns);
            }
        }
    }
}
