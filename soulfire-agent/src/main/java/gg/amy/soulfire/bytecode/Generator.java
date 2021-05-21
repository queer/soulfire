package gg.amy.soulfire.bytecode;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author amy
 * @since 5/21/21.
 */
public abstract class Generator extends BytecodeMangler {
    protected Generator(final String classToInject) {
        super(classToInject);
    }

    public byte[] generate() {
        try(final var stream = new BufferedInputStream(Objects.requireNonNull(getClass().getResourceAsStream('/' + classToInject + ".class")))) {
            final var bytes = stream.readAllBytes();
            final var cn = new ClassNode();
            final var cr = new ClassReader(bytes);
            cr.accept(cn, 0);
            inject(cr, cn);
            final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            cn.accept(cw);
            @SuppressWarnings("UnnecessaryLocalVariable")
            final byte[] cwBytes = cw.toByteArray();
            // THIS LOADS THE CLASS
            // CheckClassAdapter.verify(new ClassReader(cwBytes), true, new PrintWriter(System.err));
            return cwBytes;
        } catch(final IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
