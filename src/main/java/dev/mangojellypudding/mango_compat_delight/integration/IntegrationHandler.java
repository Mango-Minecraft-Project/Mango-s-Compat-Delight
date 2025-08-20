package dev.mangojellypudding.mango_compat_delight.integration;

import dev.mangojellypudding.mango_compat_delight.MCDUtils;
import dev.mangojellypudding.mango_compat_delight.integration.ftbultimine.MCDFTBUltimine;

import static dev.mangojellypudding.mango_compat_delight.MangoCompatDelight.LOGGER;

public class IntegrationHandler {
    public static void init() {
        if (MCDUtils.Platform.isLoaded("ftbultimine")) {
            LOGGER.info("[Mango's Compat Delight] FTB Ultimine detected, initializing integration.");
            MCDFTBUltimine.init();
        }
    }
}
