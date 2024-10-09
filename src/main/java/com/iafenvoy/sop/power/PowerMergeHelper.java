package com.iafenvoy.sop.power;

import com.iafenvoy.neptune.object.DamageUtil;
import com.iafenvoy.neptune.util.RandomHelper;
import com.iafenvoy.sop.item.block.AbstractSongCubeBlock;
import com.iafenvoy.sop.item.block.entity.AbstractSongCubeBlockEntity;
import com.iafenvoy.sop.world.FakeExplosionBehavior;
import com.iafenvoy.sop.world.ShrineStructureHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class PowerMergeHelper {
    private static final Map<PlayerEntity, MergeData> data = new HashMap<>();

    public static void run(SongPowerData songPowerData, PlayerEntity player, ServerWorld serverWorld) {
        if (!data.containsKey(player)) data.put(player, new MergeData());
        MergeData mergeData = data.get(player);
        if (player.isSneaking()) {
            Direction[] dirs = Direction.getEntityFacingOrder(player);
            if (dirs[0].getAxis() != Direction.Axis.Y) {
                if (mergeData.sneakPos == null) {
                    mergeData.sneakPos = player.getBlockPos();
                    mergeData.lastDir = dirs[0];
                }
                if (dirs[0] == mergeData.lastDir && player.getBlockPos().equals(mergeData.sneakPos)) {
                    mergeData.sneakTick++;
                    BlockPos songPos = mergeData.sneakPos.add(dirs[0].getVector().multiply(6)).add(0, 1, 0);
                    BlockState songState = serverWorld.getBlockState(songPos);
                    if (songState.getBlock() instanceof AbstractSongCubeBlock songCubeBlock && serverWorld.getBlockEntity(songPos) instanceof AbstractSongCubeBlockEntity blockEntity && ShrineStructureHelper.match(mergeData.sneakPos, serverWorld)) {
                        Vec3d center = songPos.toCenterPos();
                        if (mergeData.sneakTick >= 20 && mergeData.sneakTick <= 60) {
                            serverWorld.spawnParticles(ParticleTypes.FLAME, center.getX(), center.getY(), center.getZ(), 2, RandomHelper.nextDouble(-0.5, 0.5), RandomHelper.nextDouble(-0.5, 0.5), RandomHelper.nextDouble(-0.5, 0.5), 0.1);
                        }
                        if (mergeData.sneakTick == 60) {
                            SongPowerData.SinglePowerData d = songPowerData.get(songCubeBlock.getCategory());
                            AbstractSongPower<?> newPower = blockEntity.getPower();
                            if (d.hasPower()) blockEntity.setPower(d.getActivePower());
                            else serverWorld.breakBlock(songPos, false);
                            d.setActivePower(newPower);
                            serverWorld.createExplosion(player, DamageUtil.build(player, DamageTypes.EXPLOSION), new FakeExplosionBehavior(), center, 1, false, World.ExplosionSourceType.NONE);
                        }
                        return;
                    }
                }
            }
        }
        mergeData.reset();
    }

    private static class MergeData {
        public Direction lastDir = Direction.UP;
        public BlockPos sneakPos = null;
        public int sneakTick = 0;

        public void reset() {
            this.lastDir = Direction.UP;
            this.sneakPos = null;
            this.sneakTick = 0;
        }
    }
}
