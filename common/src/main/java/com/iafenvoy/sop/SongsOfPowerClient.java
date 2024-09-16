package com.iafenvoy.sop;

import com.iafenvoy.sop.registry.SopKeybindings;
import com.iafenvoy.sop.registry.SopRenderers;

public class SongsOfPowerClient {
    public static void init() {
        SopKeybindings.init();
        SopRenderers.registerEntityRenderer();
    }

    public static void process() {
    }
}
