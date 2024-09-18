package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.entity.AggroSphereEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class SopEntities {
    public static final EntityType<AggroSphereEntity> AGGRO_SPHERE = build("aggro_sphere", AggroSphereEntity::new, SpawnGroup.MISC, 64, 1, false, 0.5F, 0.5F);

    private static <T extends Entity> EntityType<T> build(String name, EntityType.EntityFactory<T> constructor, SpawnGroup category, int trackingRange, int updateInterval, boolean fireImmune, float sizeX, float sizeY) {
        EntityType.Builder<T> builder = EntityType.Builder.create(constructor, category).maxTrackingRange(trackingRange).trackingTickInterval(updateInterval).setDimensions(sizeX, sizeY);
        if (fireImmune) builder.makeFireImmune();
        return register(name, builder.build(name));
    }

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(SongsOfPower.MOD_ID, name), type);
    }

    public static void init() {
    }
}
