package gg.amy.soulfire.bytecode.injectors;

import com.github.hervian.reflection.Fun;
import gg.amy.soulfire.api.minecraft.block.BlockPos;
import gg.amy.soulfire.api.minecraft.entity.Player;
import gg.amy.soulfire.api.minecraft.item.InteractionHand;
import gg.amy.soulfire.api.minecraft.world.World;
import gg.amy.soulfire.bytecode.ClassMap;
import gg.amy.soulfire.bytecode.Injector;
import gg.amy.soulfire.bytecode.mapping.MappedClass;
import gg.amy.soulfire.events.Hooks;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.tree.*;

/**
 * @author amy
 * @since 5/23/21.
 */
public class BlockBehaviourInjector extends Injector {
    private final MappedClass block = ClassMap.lookup("net.minecraft.world.level.block.Block");
    private final MappedClass blockBehaviour = ClassMap.lookup("net.minecraft.world.level.block.state.BlockBehaviour");
    private final MappedClass itemInteraction = ClassMap.lookup("net.minecraft.world.InteractionResult");

    public BlockBehaviourInjector() {
        super(ClassMap.lookup("net.minecraft.world.level.block.state.BlockBehaviour").obfName());
    }

    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        final var use = blockBehaviour.method("use(net.minecraft.world.level.block.state.BlockState,net.minecraft.world.level.Level,net.minecraft.core.BlockPos,net.minecraft.world.entity.player.Player,net.minecraft.world.InteractionHand,net.minecraft.world.phys.BlockHitResult)");
        for(final var mn : cn.methods) {
            if(mn.name.equals(use.obfName()) && mn.desc.equals(use.descNoComma())) {
                final var insns = new InsnList();

                insns.add(new VarInsnNode(ALOAD, 0));
                insns.add(new TypeInsnNode(CHECKCAST, $(block.obfName())));
                insns.add(new VarInsnNode(ALOAD, 2));
                insns.add(new TypeInsnNode(CHECKCAST, $(World.class)));
                insns.add(new VarInsnNode(ALOAD, 3));
                insns.add(new TypeInsnNode(CHECKCAST, $(BlockPos.class)));
                insns.add(new VarInsnNode(ALOAD, 4));
                insns.add(new TypeInsnNode(CHECKCAST, $(Player.class)));
                insns.add(new VarInsnNode(ALOAD, 5));
                insns.add(new TypeInsnNode(CHECKCAST, $(InteractionHand.class)));
                insns.add(new MethodInsnNode(INVOKESTATIC, $(Hooks.class), Fun.toMethod(Hooks::fireBlockUse).getName(), Method.getMethod(Fun.toMethod(Hooks::fireBlockUse)).getDescriptor()));
                insns.add(new TypeInsnNode(CHECKCAST, $(itemInteraction.obfName())));
                insns.add(new InsnNode(ARETURN));

                mn.instructions.clear();
                mn.instructions.add(insns);
            }
        }
    }
}
