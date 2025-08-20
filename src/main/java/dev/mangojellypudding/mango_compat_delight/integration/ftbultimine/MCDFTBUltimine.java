package dev.mangojellypudding.mango_compat_delight.integration.ftbultimine;

import dev.ftb.mods.ftbultimine.api.crop.RegisterCropLikeEvent;
import dev.ftb.mods.ftbultimine.api.rightclick.RegisterRightClickHandlerEvent;

public class MCDFTBUltimine {
    public static void init() {
        RegisterRightClickHandlerEvent.REGISTER.register(dispatcher -> dispatcher.registerHandler(FarmerDelightRightClickHandler.INSTANCE));
        RegisterCropLikeEvent.REGISTER.register(dispatcher -> dispatcher.registerHandler(FarmerDelightCropLikeHandler.INSTANCE));
    }
}
