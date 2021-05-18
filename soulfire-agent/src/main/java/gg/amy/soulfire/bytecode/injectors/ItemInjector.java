package gg.amy.soulfire.bytecode.injectors;

import gg.amy.soulfire.api.minecraft.item.Item;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author amy
 * @since 5/17/21.
 */
public class ItemInjector extends Injector {
    public ItemInjector() {
        super(ClassMap.lookup("net.minecraft.world.item.Item").obfName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        cn.interfaces.add($(Item.class));
    }
}