package gg.amy.ingot.test;

import gg.amy.ingot.api.ClientBrandRetriever;
import gg.amy.ingot.api.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author amy
 * @since 5/16/21.
 */
@SuppressWarnings("unused")
public final class Events {
    private static final Logger LOGGER = LogManager.getLogger();

    private Events() {
    }

    public static void gameStarted() {
        LOGGER.info("#################################################################");
        LOGGER.info("ingot running on version {} ({})", Minecraft.getInstance().getLaunchedVersion(), ClientBrandRetriever.getClientModName());
        LOGGER.info("#################################################################");
    }
}
