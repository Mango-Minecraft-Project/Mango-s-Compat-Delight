package dev.mangojellypudding.mango_compat_delight;

import com.mojang.logging.LogUtils;
import dev.mangojellypudding.mango_compat_delight.integration.IntegrationHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(MangoCompatDelight.MODID)
public class MangoCompatDelight {
    public static final String MODID = "mango_compat_delight";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MangoCompatDelight(IEventBus modEventBus, ModContainer modContainer) {
        IntegrationHandler.init();
    }
}
