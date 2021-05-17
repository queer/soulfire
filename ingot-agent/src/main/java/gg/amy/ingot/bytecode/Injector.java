package gg.amy.ingot.bytecode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.CheckClassAdapter;

import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @author amy
 * @since 5/16/21.
 */
public abstract class Injector implements ClassFileTransformer, Opcodes {
    protected final Logger logger = LogManager.getLogger();
    private final String classToInject;

    protected Injector(final String classToInject) {
        this.classToInject = $(classToInject);
    }

    @SuppressWarnings("SameParameterValue")
    protected static String $(final Class<?> c) {
        return $(c.getName());
    }

    protected static String $(final String s) {
        return s.replace('/', '$').replace('.', '/');
    }

    protected static String $$(final Class<?> c) {
        return $$(c.getName());
    }

    protected static String $$(final String s) {
        return 'L' + $(s) + ';';
    }

    @Override
    public final byte[] transform(final ClassLoader classLoader, final String s,
                                  final Class<?> aClass, final ProtectionDomain protectionDomain, final byte[] bytes) {
        if(classToInject.equals(s)) {
            try {
                logger.info("Injecting class: {}", classToInject);
                final ClassReader cr = new ClassReader(bytes);
                final ClassNode cn = new ClassNode();
                cr.accept(cn, 0);
                inject(cr, cn);
                final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                cn.accept(cw);
                final byte[] cwBytes = cw.toByteArray();
                CheckClassAdapter.verify(new ClassReader(cwBytes), false, new PrintWriter(System.out));
                logger.info("Finished injecting " + classToInject + '!');
                return cwBytes;
            } catch(final Throwable t) {
                logger.error("Error injecting class " + classToInject, t);
                throw new IllegalStateException("Error injecting class " + classToInject, t);
            }
        } else {
            return null;
        }
    }

    protected abstract void inject(ClassReader cr, ClassNode cn);

    public String classToInject() {
        return classToInject;
    }
}
