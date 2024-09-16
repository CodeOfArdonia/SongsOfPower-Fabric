package com.iafenvoy.sop.entity;

import com.iafenvoy.neptune.object.DamageUtil;
import com.iafenvoy.neptune.util.RandomHelper;
import com.iafenvoy.sop.world.FakeExplosionBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class AggroSphereEntity extends PersistentProjectileEntity {
    public AggroSphereEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getY() > 1000 || this.age > 20 * 60)
            this.remove(RemovalReason.DISCARDED);
        if (this.isOnGround() || this.inGround || !this.getEntityWorld().getBlockState(this.getBlockPos()).isAir()) {
            this.getEntityWorld().createExplosion(this, DamageUtil.build(this, DamageTypes.MAGIC), new FakeExplosionBehavior(), this.getPos(), 2, false, World.ExplosionSourceType.NONE);
            this.remove(RemovalReason.DISCARDED);
        }
        LivingEntity target = this.getEntityWorld().getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, null, this.getX(), this.getY(), this.getZ(), new Box(this.getPos().add(1, 1, 1), this.getPos().subtract(1, 1, 1)));
        if (target != null) {
            if (target.getMainHandStack().isOf(Items.SHIELD))
                target.getMainHandStack().damage(5, target.getRandom(), null);
            else if (target.getOffHandStack().isOf(Items.SHIELD))
                target.getOffHandStack().damage(5, target.getRandom(), null);
            else target.damage(DamageUtil.build(target, DamageTypes.MAGIC), 5);
            this.remove(RemovalReason.DISCARDED);
        }
        for (int i = 0; i < 9; i++)
            this.getEntityWorld().addParticle(ParticleTypes.FLAME, this.getX() + RandomHelper.nextDouble(-0.3, 0.3), this.getY() + RandomHelper.nextDouble(-0.3, 0.3) + 0.25, this.getZ() + RandomHelper.nextDouble(-0.3, 0.3), this.getVelocity().getX(), this.getVelocity().getY(), this.getVelocity().getZ());
    }

    @Override
    public boolean isNoClip() {
        return true;
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.FIRE_CHARGE);
    }
}
