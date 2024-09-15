package com.iafenvoy.sop.fabric;

import com.iafenvoy.sop.SongsOfPowerClient;
import net.fabricmc.api.ClientModInitializer;

public class SongsOfPowerFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SongsOfPowerClient.init();
        SongsOfPowerClient.process();
    }
}
