package com.iafenvoy.sop;

import com.iafenvoy.sop.power.PowerType;
import com.iafenvoy.sop.power.SongPowerData;
import com.iafenvoy.sop.registry.SopCommands;
import com.iafenvoy.sop.registry.SopItemGroups;
import com.iafenvoy.sop.registry.SopItems;
import com.iafenvoy.sop.registry.SopPowers;
import dev.architectury.networking.NetworkManager;

public class SongsOfPower {
    public static final String MOD_ID = "sop";

    public static void init() {
        SopItems.REGISTRY.register();
        SopItemGroups.REGISTRY.register();
        SopPowers.init();
        SopCommands.init();
    }

    public static void process() {
        SopItems.init();
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, Static.KEYBINDING_SYNC, (buf, context) -> {
            PowerType type = buf.readEnumConstant(PowerType.class);
            context.queue(() -> SongPowerData.byPlayer(context.getPlayer()).get(type).keyPress());
        });
    }
}
