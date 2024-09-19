package com.iafenvoy.sop.render;

import com.iafenvoy.sop.entity.AggroSphereEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AggroSphereRenderer extends EntityRenderer<AggroSphereEntity> {
    private final SphereModel<AggroSphereEntity> sphereModel;

    public AggroSphereRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.sphereModel = new SphereModel<>(SphereModel.createBodyLayer().createModel());
    }

    @Override
    public void render(AggroSphereEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.translate(0, -0.5, 0);
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(SphereModel.WHITE_TEXTURE));
        this.sphereModel.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, 1, 0, 0, 0.3f);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(AggroSphereEntity entity) {
        return SphereModel.WHITE_TEXTURE;
    }
}
