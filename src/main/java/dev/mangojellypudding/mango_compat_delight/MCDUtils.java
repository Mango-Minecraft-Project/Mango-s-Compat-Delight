package dev.mangojellypudding.mango_compat_delight;

import net.neoforged.fml.ModList;

public interface MCDUtils {
    interface Platform {
        static boolean isLoaded(String modId) {
            return ModList.get().isLoaded(modId);
        }
    }
}
