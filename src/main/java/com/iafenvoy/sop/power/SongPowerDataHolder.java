package com.iafenvoy.sop.power;

import com.iafenvoy.neptune.util.RandomHelper;
import com.iafenvoy.sop.entity.SopProjectileEntity;
import com.iafenvoy.sop.registry.SopGameRules;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SongPowerDataHolder {
    private final SongPowerData.SinglePowerData data;
    private boolean cancelled = false;

    public SongPowerDataHolder(SongPowerData.SinglePowerData data) {
        this.data = data;
    }

    public SongPowerData.SinglePowerData getData() {
        return this.data;
    }

    public PlayerEntity getPlayer() {
        return this.data.getPlayer();
    }

    public World getWorld() {
        return this.data.getPlayer().getEntityWorld();
    }

    public boolean enableParticle() {
        return this.getWorld().getGameRules().getBoolean(SopGameRules.SHOW_PARTICLE);
    }

    public void spawnParticle(ParticleEffect effect, boolean follow) {
        if (this.enableParticle()) {
            PlayerEntity player = this.getPlayer();
            Vec3d velocity = follow ? player.getVelocity() : Vec3d.ZERO;
            this.getWorld().addParticle(effect,
                    RandomHelper.rangeRand(player.getX(),0.5),
                    RandomHelper.rangeRand(player.getY(),0.5),
                    RandomHelper.rangeRand(player.getZ(),0.5),
                    RandomHelper.rangeRand(velocity.x,0.1),
                    RandomHelper.rangeRand(velocity.y,0.1),
                    RandomHelper.rangeRand(velocity.z,0.1));
        }
    }

    public boolean usingWeapon() {
        return this.getPlayer().getMainHandStack().getItem() instanceof SwordItem;
    }

    public void processProjectile(SopProjectileEntity projectile) {
        projectile.setOwner(this.getPlayer());
        if (this.usingWeapon()) projectile.setCritical();
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void cooldown() {
        this.data.cooldown();
    }
}
