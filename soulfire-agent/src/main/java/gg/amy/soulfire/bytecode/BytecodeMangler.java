package gg.amy.soulfire.bytecode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 5/16/21.
 */
public abstract class BytecodeMangler implements Opcodes {
    protected final Logger logger = LogManager.getLogger();
    protected final String classToInject;

    protected BytecodeMangler(final String classToInject) {
        this.classToInject = $(classToInject);
    }

    @SuppressWarnings("SameParameterValue")
    protected static String $(@Nonnull final Class<?> c) {
        return $(c.getName());
    }

    protected static String $(@Nonnull final String s) {
        return s.replace('/', '$').replace('.', '/');
    }

    @SuppressWarnings("SameParameterValue")
    protected static String $$(@Nonnull final Class<?> c) {
        return $$(c.getName());
    }

    protected static String $$(@Nonnull final String s) {
        if(s.startsWith("L") && s.endsWith(";")) {
            return s;
        } else {
            return 'L' + $(s) + ';';
        }
    }

    protected abstract void inject(ClassReader cr, ClassNode cn);

    public String classToInject() {
        return classToInject;
    }
}
