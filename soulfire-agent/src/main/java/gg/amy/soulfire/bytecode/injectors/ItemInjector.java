package gg.amy.soulfire.bytecode.injectors;

import com.github.hervian.reflection.Fun;
import gg.amy.soulfire.api.minecraft.item.InteractionResult;
import gg.amy.soulfire.api.minecraft.item.Item;
import gg.amy.soulfire.api.minecraft.item.ItemUseContext;
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
public class ItemInjector extends Injector {
    private final MappedClass item = ClassMap.lookup("net.minecraft.world.item.Item");
    private final MappedClass itemInteraction = ClassMap.lookup("net.minecraft.world.InteractionResult");

    public ItemInjector() {
        super(ClassMap.lookup("net.minecraft.world.item.Item").obfName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        final var useOn = item.method("useOn(net.minecraft.world.item.context.UseOnContext)");
        for(final var mn : cn.methods) {
            if(mn.name.equals(useOn.obfName()) && mn.desc.equals(useOn.descNoComma())) {
                final var insns = new InsnList();

                insns.add(new VarInsnNode(ALOAD, 0));
                insns.add(new VarInsnNode(ALOAD, 1));
                insns.add(new MethodInsnNode(INVOKESTATIC, $(Hooks.class), Fun.toMethod(Hooks::fireItemUse).getName(), String.format("(%s%s)%s", $$(Item.class), $$(ItemUseContext.class), $$(InteractionResult.class))));
                insns.add(new TypeInsnNode(CHECKCAST, $(itemInteraction.obfName())));
                insns.add(new InsnNode(ARETURN));

                mn.instructions.clear();
                mn.instructions.add(insns);
            }
        }
    }
}
