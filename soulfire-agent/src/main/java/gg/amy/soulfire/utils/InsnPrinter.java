package gg.amy.soulfire.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

import javax.annotation.Nonnull;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author amy
 * @since 5/18/21.
 */
@SuppressWarnings("unused")
public final class InsnPrinter {
    private static final Logger LOGGER = LogManager.getLogger();

    private InsnPrinter() {
    }

    public static void print(@Nonnull final InsnList insns) {
        final var printer = new Textifier();
        final var mp = new TraceMethodVisitor(printer);
        for(final var insn : insns) {
            insn.accept(mp);
            final var sw = new StringWriter();
            printer.print(new PrintWriter(sw));
            printer.getText().clear();
            LOGGER.info("Visited insn: {}", sw.toString().trim());
        }
    }
}
