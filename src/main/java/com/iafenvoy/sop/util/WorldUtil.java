package com.iafenvoy.sop.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WorldUtil {
    @Nullable
    public static EntityHitResult raycastEntity(LivingEntity entity, double maxDistance) {
        World world = entity.getEntityWorld();
        List<LivingEntity> livingEntities = world.getEntitiesByClass(LivingEntity.class, new Box(entity.getPos().add(maxDistance, maxDistance, maxDistance), entity.getPos().subtract(maxDistance, maxDistance, maxDistance)), living -> entity != living);
        double distance = Double.MAX_VALUE;
        LivingEntity cache = null;
        for (LivingEntity living : livingEntities) {
            double d = entity.distanceTo(living);
            if (entity.canSee(living) && d < distance) {
                distance = d;
                cache = living;
            }
        }
        if (cache == null) return null;
        return new EntityHitResult(cache);
    }
}
