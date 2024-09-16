package com.iafenvoy.sop;

import com.iafenvoy.sop.power.PowerType;
import com.iafenvoy.sop.power.SongPowerData;
import com.iafenvoy.sop.registry.*;
import dev.architectury.networking.NetworkManager;

public class SongsOfPower {
    public static final String MOD_ID = "sop";

    public static void init() {
        SopEntities.REGISTRY.register();
        SopItems.REGISTRY.register();
        SopItemGroups.REGISTRY.register();
        SopSounds.REGISTRY.register();
        SopPowers.init();
        SopCommands.init();
    }

    public static void process() {
        SopItems.init();
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, Static.KEYBINDING_SYNC, (buf, context) -> {
            PowerType type = buf.readEnumConstant(PowerType.class);
            if (!context.getPlayer().isSpectator())
                context.queue(() -> SongPowerData.byPlayer(context.getPlayer()).get(type).keyPress());
        });
    }
}
