package com.iafenvoy.sop;

import com.iafenvoy.sop.registry.SopBlocks;
import com.iafenvoy.sop.registry.SopKeybindings;
import com.iafenvoy.sop.registry.SopRenderers;
import com.iafenvoy.sop.render.SongCubeBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;

public class SongsOfPowerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SopKeybindings.init();
        SopRenderers.registerEntityRenderer();

        BlockEntityRendererRegistryImpl.register(SopBlocks.AGGRESSIUM_SONG_TYPE, SongCubeBlockEntityRenderer.AggressiumSongCubeBlockEntityRenderer::new);
        BlockEntityRendererRegistryImpl.register(SopBlocks.MOBILIUM_SONG_TYPE, SongCubeBlockEntityRenderer.MobiliumSongCubeBlockEntityRenderer::new);
        BlockEntityRendererRegistryImpl.register(SopBlocks.PROTISIUM_SONG_TYPE, SongCubeBlockEntityRenderer.ProtisiumSongCubeBlockEntityRenderer::new);
        BlockEntityRendererRegistryImpl.register(SopBlocks.SUPPORTIUM_SONG_TYPE, SongCubeBlockEntityRenderer.SupportiumSongCubeBlockEntityRenderer::new);
    }
}
