package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.render.AggroSphereRenderer;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;

public class SopRenderers {
    public static void registerEntityRenderer() {
        EntityRendererRegistry.register(SopEntities.AGGRO_SPHERE, AggroSphereRenderer::new);
    }
}
