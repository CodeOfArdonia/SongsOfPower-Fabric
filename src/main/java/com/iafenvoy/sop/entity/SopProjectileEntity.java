package com.iafenvoy.sop.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.world.World;

public abstract class SopProjectileEntity extends PersistentProjectileEntity {
    private float damageMultiplier = 1f;

    protected SopProjectileEntity(EntityType<? extends SopProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public float transformDamage(float base) {
        return base * this.damageMultiplier;
    }

    public void setCritical() {
        this.damageMultiplier = 1.5f;
    }
}
