package gg.amy.soulfire.test;

import gg.amy.soulfire.api.minecraft.ClientBrandRetriever;
import gg.amy.soulfire.api.minecraft.Minecraft;
import gg.amy.soulfire.api.minecraft.item.ItemProperties;
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
        LOGGER.info("soulfire running on version {} (brand: {})", Minecraft.getInstance().launchedVersion(), ClientBrandRetriever.clientModName());
        LOGGER.info("soulfire created properties: {}", ItemProperties.create());
    }
}
