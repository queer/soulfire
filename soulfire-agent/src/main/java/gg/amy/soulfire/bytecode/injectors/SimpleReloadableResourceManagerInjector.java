package gg.amy.soulfire.bytecode.injectors;

import gg.amy.soulfire.api.minecraft.resource.SimpleReloadableResourceManager;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import gg.amy.soulfire.events.Hooks;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

/**
 * @author amy
 * @since 5/19/21.
 */
public class SimpleReloadableResourceManagerInjector extends Injector {
    private final MappedClass repo = ClassMap.lookup("net.minecraft.server.packs.resources.SimpleReloadableResourceManager");

    public SimpleReloadableResourceManagerInjector() {
        super(ClassMap.lookup("net.minecraft.server.packs.resources.SimpleReloadableResourceManager").obfName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        final var reload = repo.method("createFullReload(java.util.concurrent.Executor,java.util.concurrent.Executor,java.util.concurrent.CompletableFuture,java.util.List)");
        final var clear = repo.method("clear()");
        for(final var mn : cn.methods) {
            if(mn.name.equals(reload.obfName()) && mn.desc.equals(reload.descNoComma())) {
                AbstractInsnNode clearInsn = null;
                for(final var insn : mn.instructions) {
                    if(insn instanceof MethodInsnNode min) {
                        if(min.name.equals(clear.obfName()) && min.desc.equals(clear.descNoComma())) {
                            clearInsn = insn;
                            break;
                        }
                    }
                }
                if(clearInsn == null) {
                    throw new IllegalStateException("clear == null!?");
                }
                final var insns = new InsnList();
                insns.add(new VarInsnNode(ALOAD, 0));
                insns.add(new MethodInsnNode(INVOKESTATIC, $(Hooks.class), "fireResourceManagerReload", String.format("(%s)V", $$(SimpleReloadableResourceManager.class)), false));
                mn.instructions.insert(clearInsn, insns);
            }
        }
    }
}
