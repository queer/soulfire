package gg.amy.soulfire.bytecode.injectors;

import gg.amy.soulfire.api.Soulfire;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import gg.amy.soulfire.events.Hooks;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

/**
 * @author amy
 * @since 5/16/21.
 */
public class MinecraftInjector extends Injector {
    private final MappedClass minecraft = ClassMap.lookup("net.minecraft.client.Minecraft");
    private final MappedClass modelManager = ClassMap.lookup("net.minecraft.client.resources.model.ModelManager");

    public MinecraftInjector() {
        super(ClassMap.lookup("net.minecraft.client.Minecraft").obfName(), true);
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        for(final var mn : cn.methods) {
            if(mn.name.equals("<init>")) {
                AbstractInsnNode putGameDir = null;
                AbstractInsnNode modelManagerInit = null;
                AbstractInsnNode ret = null;

                for(final var insn : mn.instructions) {
                    if(insn instanceof FieldInsnNode fin) {
                        if(fin.getOpcode() == PUTFIELD && fin.name.equals(minecraft.field("gameDirectory").obfName())) {
                            putGameDir = fin;
                        }
                    }

                    if(insn instanceof MethodInsnNode min) {
                        if(min.getOpcode() == INVOKESPECIAL && min.name.equals("<init>") && min.owner.equals(modelManager.obfName())) {
                            modelManagerInit = min.getNext();
                        }
                    }

                    if(insn.getOpcode() == RETURN) {
                        ret = insn;
                    }
                }

                {
                    if(putGameDir == null) {
                        throw new IllegalStateException("Couldn't find gameDirectory!");
                    }
                    final var insns = new InsnList();
                    insns.add(new MethodInsnNode(INVOKESTATIC, $(Soulfire.class), "soulfire", "()" + $$(Soulfire.class), true));
                    insns.add(new MethodInsnNode(INVOKEINTERFACE, $(Soulfire.class), "init", "()V", true));
                    insns.add(new MethodInsnNode(INVOKESTATIC, $(Hooks.class), "fireInit", "()V", false));
                    mn.instructions.insert(putGameDir, insns);
                }

                {
                    if(modelManagerInit == null) {
                        throw new IllegalStateException("Couldn't find ModelManager#<init>!");
                    }
                    final var insns = new InsnList();
                    insns.add(new MethodInsnNode(INVOKESTATIC, $(Hooks.class), "fireResourceLoad", "()V", false));
                    mn.instructions.insert(modelManagerInit, insns);
                }

                {
                    if(ret == null) {
                        throw new IllegalStateException("Somehow couldn't find RETURN (seriously wtf???)!");
                    }
                    final var insns = new InsnList();
                    insns.add(new MethodInsnNode(INVOKESTATIC, $(Hooks.class), "fireGameLoaded", "()V", false));
                    mn.instructions.insertBefore(ret, insns);
                }
            }
        }
    }
}
