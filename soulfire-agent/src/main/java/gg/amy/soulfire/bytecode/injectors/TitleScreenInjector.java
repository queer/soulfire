package gg.amy.soulfire.bytecode.injectors;

import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

/**
 * @author amy
 * @since 5/17/21.
 */
public class TitleScreenInjector extends Injector {
    private final MappedClass titleScreen = ClassMap.lookup("net.minecraft.client.gui.screens.TitleScreen");

    public TitleScreenInjector() {
        super(ClassMap.lookup("net.minecraft.client.gui.screens.TitleScreen").obfName(), true);
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        final var screen = ClassMap.lookup("net.minecraft.client.gui.screens.Screen");
        final var font = ClassMap.lookup("net.minecraft.client.gui.Font");
        final var component = ClassMap.lookup("net.minecraft.client.gui.GuiComponent");

        final var render = titleScreen.method("render(com.mojang.blaze3d.vertex.PoseStack,int,int,float)");
        final var drawString = component.method("drawString(com.mojang.blaze3d.vertex.PoseStack,net.minecraft.client.gui.Font,java.lang.String,int,int,int)");

        for(final var m : cn.methods) {
            if(m.name.equals(render.obfName()) && m.desc.equals(render.descNoComma())) {
                AbstractInsnNode node = null;
                for(final var insn : m.instructions) {
                    if(insn instanceof LdcInsnNode ldc) {
                        if(ldc.cst instanceof String s && s.startsWith("Minecraft")) {
                            node = insn;
                        }
                    }
                }
                final var insns = new InsnList();
                insns.add(new VarInsnNode(ALOAD, 1));
                insns.add(new VarInsnNode(ALOAD, 0));
                insns.add(new FieldInsnNode(GETFIELD, screen.obfName(), screen.fields().get("font").obfName(), $$(font.obfName())));
                insns.add(new LdcInsnNode("Modded with soulfire"));
                insns.add(new LdcInsnNode(2));
                insns.add(new LdcInsnNode(2));
                insns.add(new LdcInsnNode(0xFFFFFFFF));
                insns.add(new MethodInsnNode(INVOKESTATIC, component.obfName(), drawString.obfName(), drawString.descNoComma(), false));
                m.instructions.insertBefore(node, insns);
                logger.info("Injected main menu rendering");
            }
        }
    }
}
