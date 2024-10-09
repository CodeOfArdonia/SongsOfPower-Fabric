package com.iafenvoy.sop;

import com.iafenvoy.jupiter.ServerConfigManager;
import com.iafenvoy.jupiter.malilib.config.ConfigManager;
import com.iafenvoy.sop.config.SopConfig;
import com.iafenvoy.sop.power.PowerCategory;
import com.iafenvoy.sop.power.SongPowerData;
import com.iafenvoy.sop.registry.*;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;

public class SongsOfPower implements ModInitializer {
    public static final String MOD_ID = "sop";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        ConfigManager.getInstance().registerConfigHandler(SopConfig.INSTANCE);
        ConfigManager.getInstance().registerServerConfig(SopConfig.INSTANCE, ServerConfigManager.PermissionChecker.IS_OPERATOR);

        SopBlocks.init();
        SopCommands.init();
        SopEntities.init();
        SopGameRules.init();
        SopItems.init();
        SopItemGroups.init();
        SopParticles.init();
        SopPowers.init();
        SopSounds.init();

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity instanceof ServerPlayerEntity player) {
                if (!player.getEntityWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY))
                    SongPowerData.byPlayer(player).dropAll();
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(Static.KEYBINDING_SYNC, (server, player, handler, buf, responseSender) -> {
            PowerCategory type = buf.readEnumConstant(PowerCategory.class);
            SongPowerData data = SongPowerData.byPlayer(player);
            if (!player.isSpectator() && data.isEnabled())
                server.execute(() -> data.get(type).keyPress());
        });
    }
}
