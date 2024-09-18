package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.render.AggroSphereRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public final class SopRenderers {
    public static void registerEntityRenderer() {
        EntityRendererRegistry.register(SopEntities.AGGRO_SPHERE, AggroSphereRenderer::new);
    }
}
