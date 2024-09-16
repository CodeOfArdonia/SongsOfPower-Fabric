package com.iafenvoy.sop;

import com.iafenvoy.sop.registry.SopKeybindings;
import com.iafenvoy.sop.registry.SopRenderers;
import net.fabricmc.api.ClientModInitializer;

public class SongsOfPowerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SopKeybindings.init();
        SopRenderers.registerEntityRenderer();
    }
}
