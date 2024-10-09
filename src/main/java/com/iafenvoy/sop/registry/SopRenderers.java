package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.particle.SongEffectParticle;
import com.iafenvoy.sop.render.AggroSphereRenderer;
import com.iafenvoy.sop.render.SongCubeBlockEntityRenderer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;

public final class SopRenderers {
    public static void registerEntityRenderer() {
        EntityRendererRegistry.register(SopEntities.AGGRO_SPHERE, AggroSphereRenderer::new);
    }

    public static void registerParticleRenderer() {
        ParticleFactoryRegistry.getInstance().register(SopParticles.SONG_EFFECT, SongEffectParticle::create);
    }

    public static void registerBlockEntityRenderer() {
        BlockEntityRendererRegistryImpl.register(SopBlocks.AGGRESSIUM_SONG_TYPE, SongCubeBlockEntityRenderer.AggressiumSongCubeBlockEntityRenderer::new);
        BlockEntityRendererRegistryImpl.register(SopBlocks.MOBILIUM_SONG_TYPE, SongCubeBlockEntityRenderer.MobiliumSongCubeBlockEntityRenderer::new);
        BlockEntityRendererRegistryImpl.register(SopBlocks.PROTISIUM_SONG_TYPE, SongCubeBlockEntityRenderer.ProtisiumSongCubeBlockEntityRenderer::new);
        BlockEntityRendererRegistryImpl.register(SopBlocks.SUPPORTIUM_SONG_TYPE, SongCubeBlockEntityRenderer.SupportiumSongCubeBlockEntityRenderer::new);
    }
}
