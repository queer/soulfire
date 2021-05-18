package gg.amy.soulfire.bytecode.injectors;

import gg.amy.soulfire.api.minecraft.registry.Registries.ResourceKey;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author amy
 * @since 5/17/21.
 */
public class ResourceKeyInjector extends Injector {
    private final MappedClass resourceKey = ClassMap.lookup("net.minecraft.resources.ResourceKey");

    public ResourceKeyInjector() {
        super(ClassMap.lookup("net.minecraft.resources.ResourceKey").obfName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        cn.interfaces.add($(ResourceKey.class));
    }
}
