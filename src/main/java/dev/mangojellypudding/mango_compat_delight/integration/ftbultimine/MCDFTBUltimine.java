package dev.mangojellypudding.mango_compat_delight.integration.ftbultimine;

import dev.ftb.mods.ftbultimine.api.crop.RegisterCropLikeEvent;
import dev.ftb.mods.ftbultimine.api.rightclick.RegisterRightClickHandlerEvent;

public class MCDFTBUltimine {
    public MCDFTBUltimine() {
        RegisterRightClickHandlerEvent.REGISTER.register(this::registerRightClickHandlers);
        RegisterCropLikeEvent.REGISTER.register(this::registerCropLikeEvent);
    }

    private void registerCropLikeEvent(RegisterCropLikeEvent.Dispatcher dispatcher) {
//        dispatcher.registerHandler(FDMushroomColonyLikeHandler.INSTANCE);
    }

    private void registerRightClickHandlers(RegisterRightClickHandlerEvent.Dispatcher dispatcher) {
        dispatcher.registerHandler(FDRightClickHandler.INSTANCE);
        dispatcher.registerHandler(FDCropHarvesting.INSTANCE);
    }
}
