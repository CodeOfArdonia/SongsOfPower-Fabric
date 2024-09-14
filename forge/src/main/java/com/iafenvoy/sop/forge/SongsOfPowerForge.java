package com.iafenvoy.sop.forge;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.power.SongPowerData;
import com.iafenvoy.sop.forge.component.SongPowerDataProvider;
import com.iafenvoy.sop.impl.ComponentManager;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

@Mod(SongsOfPower.MOD_ID)
public class SongsOfPowerForge {
    public SongsOfPowerForge(FMLJavaModLoadingContext context) {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(SongsOfPower.MOD_ID, context.getModEventBus());
        SongsOfPower.init();
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof PlayerEntity player) {
                boolean isServerNotFake = player instanceof ServerPlayerEntity && !(player instanceof FakePlayer);
                if ((isServerNotFake || player instanceof AbstractClientPlayerEntity) && !player.getCapability(SongPowerDataProvider.CAPABILITY).isPresent())
                    event.addCapability(new Identifier(SongsOfPower.MOD_ID, "song_power"), new SongPowerDataProvider(player));
            }
        }

        @SubscribeEvent
        public static void onServerTick(TickEvent.ServerTickEvent event) {
            MinecraftServer server = event.getServer();
            List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
            for (PlayerEntity player : players) {
                SongPowerData data = ComponentManager.getSongPowerData(player);
                if (data != null) data.tick();
            }
        }
    }
}