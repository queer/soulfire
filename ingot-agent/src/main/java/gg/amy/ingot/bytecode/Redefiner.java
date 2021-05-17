package gg.amy.ingot.bytecode;

import org.objectweb.asm.Opcodes;

import java.lang.instrument.ClassDefinition;

/**
 * @author amy
 * @since 5/17/21.
 */
@FunctionalInterface
public interface Redefiner extends Opcodes {
    ClassDefinition redefine();
}
