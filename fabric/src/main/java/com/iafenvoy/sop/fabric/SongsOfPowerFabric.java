package com.iafenvoy.sop.fabric;

import com.iafenvoy.sop.SongsOfPower;
import net.fabricmc.api.ModInitializer;

public class SongsOfPowerFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SongsOfPower.init();
    }
}