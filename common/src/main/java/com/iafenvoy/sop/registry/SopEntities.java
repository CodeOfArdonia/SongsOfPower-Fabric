package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.entity.AggroSphereEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Supplier;

public class SopEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(SongsOfPower.MOD_ID, RegistryKeys.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<AggroSphereEntity>> AGGRO_SPHERE = build("aggro_sphere", AggroSphereEntity::new, SpawnGroup.MISC, 64, 1, false, 0.5F, 0.5F);

    private static <T extends Entity> RegistrySupplier<EntityType<T>> build(String name, EntityType.EntityFactory<T> constructor, SpawnGroup category, int trackingRange, int updateInterval, boolean fireImmune, float sizeX, float sizeY) {
        return register(name, () -> {
            EntityType.Builder<T> builder = EntityType.Builder.create(constructor, category).maxTrackingRange(trackingRange).trackingTickInterval(updateInterval).setDimensions(sizeX, sizeY);
            if (fireImmune) builder.makeFireImmune();
            return builder.build(name);
        });
    }

    private static <T extends Entity> RegistrySupplier<EntityType<T>> register(String name, Supplier<EntityType<T>> type) {
        return REGISTRY.register(name, type);
    }
}
