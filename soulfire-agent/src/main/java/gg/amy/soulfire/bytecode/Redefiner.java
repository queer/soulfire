package gg.amy.soulfire.bytecode;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
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
public abstract class Redefiner extends BytecodeMangler {
    protected Redefiner(final String classToInject) {
        super(classToInject);
    }

    public final ClassDefinition redefine() {
        logger.info("Redefining {}...", classToInject);
        try(final var stream = new BufferedInputStream(Objects.requireNonNull(getClass().getResourceAsStream('/' + classToInject + ".class")))) {
            final var bytes = stream.readAllBytes();
            final var cn = new ClassNode();
            final var cr = new ClassReader(bytes);
            cr.accept(cn, 0);
            inject(cr, cn);
            final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            cn.accept(cw);
            final byte[] cwBytes = cw.toByteArray();
            // THIS LOADS THE CLASS
            // CheckClassAdapter.verify(new ClassReader(cwBytes), true, new PrintWriter(System.err));
            return new ClassDefinition(Class.forName(classToInject.replace('/', '.')), cwBytes);
        } catch(final IOException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
