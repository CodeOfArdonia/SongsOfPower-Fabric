package com.iafenvoy.sop.render;

import com.iafenvoy.sop.item.block.entity.*;
import com.iafenvoy.sop.registry.SopBlocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public abstract class SongCubeBlockEntityRenderer<T extends AbstractSongCubeBlockEntity> implements BlockEntityRenderer<T> {
    protected BlockEntityRendererFactory.Context ctx;

    protected SongCubeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.5, 0.25, 0.5);
        this.ctx.getItemRenderer().renderItem(this.getStack(), ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.pop();
    }

    protected abstract ItemStack getStack();

    public static class AggressiumSongCubeBlockEntityRenderer extends SongCubeBlockEntityRenderer<AggressiumSongCubeBlockEntity> {
        public AggressiumSongCubeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
            super(ctx);
        }

        @Override
        protected ItemStack getStack() {
            return new ItemStack(SopBlocks.AGGRESSIUM_SONG);
        }
    }

    public static class MobiliumSongCubeBlockEntityRenderer extends SongCubeBlockEntityRenderer<MobiliumSongCubeBlockEntity> {
        public MobiliumSongCubeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
            super(ctx);
        }

        @Override
        protected ItemStack getStack() {
            return new ItemStack(SopBlocks.MOBILIUM_SONG);
        }
    }

    public static class ProtisiumSongCubeBlockEntityRenderer extends SongCubeBlockEntityRenderer<ProtisiumSongCubeBlockEntity> {
        public ProtisiumSongCubeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
            super(ctx);
        }

        @Override
        protected ItemStack getStack() {
            return new ItemStack(SopBlocks.PROTISIUM_SONG);
        }
    }

    public static class SupportiumSongCubeBlockEntityRenderer extends SongCubeBlockEntityRenderer<SupportiumSongCubeBlockEntity> {
        public SupportiumSongCubeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
            super(ctx);
        }

        @Override
        protected ItemStack getStack() {
            return new ItemStack(SopBlocks.SUPPORTIUM_SONG);
        }
    }
}
