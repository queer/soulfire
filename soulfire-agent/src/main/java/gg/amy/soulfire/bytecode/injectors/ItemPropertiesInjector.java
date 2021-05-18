package gg.amy.soulfire.bytecode.injectors;

import gg.amy.soulfire.api.minecraft.item.ItemProperties;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author amy
 * @since 5/17/21.
 */
public class ItemPropertiesInjector extends Injector {
    public ItemPropertiesInjector() {
        super(ClassMap.lookup("net.minecraft.world.item.Item$Properties").obfName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        cn.interfaces.add($(ItemProperties.class));
    }
}
