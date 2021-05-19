package gg.amy.soulfire.bytecode.injectors;

import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author amy
 * @since 5/18/21.
 */
public class ResourceKeyInjector extends Injector {
    public ResourceKeyInjector() {
        super(ClassMap.lookup("net.minecraft.resources.ResourceKey").obfName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var mn : cn.methods) {
            if(mn.name.equals("<init>")) {
                mn.access = ACC_PUBLIC;
                logger.info("{}#{} now public", cn.name, mn.name);
            }
        }
    }
}
