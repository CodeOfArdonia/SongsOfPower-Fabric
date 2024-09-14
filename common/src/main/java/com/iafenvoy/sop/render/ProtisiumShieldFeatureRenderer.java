package com.iafenvoy.sop.render;

import com.iafenvoy.sop.power.PowerType;
import com.iafenvoy.sop.power.SongPowerData;
import com.iafenvoy.sop.registry.SopPowers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public class ProtisiumShieldFeatureRenderer<T extends PlayerEntity, M extends PlayerEntityModel<T>> extends FeatureRenderer<T, M> {
    public ProtisiumShieldFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (SongPowerData.byPlayer(entity).powerEnabled(PowerType.PROTISIUM, SopPowers.PROTESPHERE)) {
            matrices.push();
            matrices.scale(2.5f, 2.5f, 2.5f);
            matrices.translate(0, -0.8, 0);
            SphereModel<T> sphereModel = new SphereModel<>(SphereModel.createBodyLayer().createModel());
            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(SphereModel.WHITE_TEXTURE));
            sphereModel.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, 0, 1, 1, 0.1f);
            matrices.pop();
        }
    }
}
