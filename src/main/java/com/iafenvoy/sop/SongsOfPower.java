package com.iafenvoy.sop;

import com.iafenvoy.sop.power.PowerCategory;
import com.iafenvoy.sop.power.SongPowerData;
import com.iafenvoy.sop.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class SongsOfPower implements ModInitializer {
    public static final String MOD_ID = "sop";

    @Override
    public void onInitialize() {
        SopCommands.init();
        SopEntities.init();
        SopItems.init();
        SopItemGroups.init();
        SopSounds.init();
        SopPowers.init();
        ServerPlayNetworking.registerGlobalReceiver(Static.KEYBINDING_SYNC, (server, player, handler, buf, responseSender) -> {
            PowerCategory type = buf.readEnumConstant(PowerCategory.class);
            SongPowerData data = SongPowerData.byPlayer(player);
            if (!player.isSpectator() && data.isEnabled())
                server.execute(() -> data.get(type).keyPress());
        });
    }
}
