package gg.amy.ingot.bytecode.redefiners;

import gg.amy.ingot.IngotAgent;
import gg.amy.ingot.api.Minecraft;
import gg.amy.ingot.bytecode.Redefiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.util.CheckClassAdapter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.instrument.ClassDefinition;
import java.util.Objects;

/**
 * @author amy
 * @since 5/17/21.
 */
public class IngotMinecraftRedefiner implements Redefiner {
    private final Logger logger = LogManager.getLogger();

    @Override
    public ClassDefinition redefine() {
        // TODO: Steal this behaviour from Injector
        final var cls = Minecraft.class.getName().replace('.', '/');
        try(final var stream = new BufferedInputStream(Objects.requireNonNull(getClass().getResourceAsStream('/' + cls + ".class")))) {
            // TODO: This is very similar to how Injector works -- can we steal this behaviour?
            final var minecraft = IngotAgent.currentMappings().get("net.minecraft.client.Minecraft");
            final var bytes = stream.readAllBytes();
            final var cn = new ClassNode();
            final var cr = new ClassReader(bytes);
            cr.accept(cn, 0);

            for(final var method : cn.methods) {
                switch(method.name) {
                    case "getInstance": {
                        final var insns = new InsnList();
                        insns.add(new MethodInsnNode(INVOKESTATIC, minecraft.obfuscatedName(), minecraft.methods().get("getInstance()"), "()" + minecraft.descriptor(), false));
                        insns.add(new InsnNode(ARETURN));

                        method.instructions.clear();
                        method.instructions.add(insns);
                        break;
                    }
                }
            }

            final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            cn.accept(cw);
            final byte[] cwBytes = cw.toByteArray();
            CheckClassAdapter.verify(new ClassReader(cwBytes), false, new PrintWriter(System.out));
            return new ClassDefinition(Minecraft.class, cwBytes);
        } catch(final IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
