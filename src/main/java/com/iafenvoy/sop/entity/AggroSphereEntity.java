package com.iafenvoy.sop.entity;

import com.iafenvoy.neptune.object.DamageUtil;
import com.iafenvoy.neptune.util.RandomHelper;
import com.iafenvoy.sop.world.FakeExplosionBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class AggroSphereEntity extends SopProjectileEntity {
    public AggroSphereEntity(EntityType<? extends AggroSphereEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getY() > 1000 || this.age > 20 * 60)
            this.remove(RemovalReason.DISCARDED);
        if (this.isOnGround() || this.inGround || !this.getEntityWorld().getBlockState(this.getBlockPos()).isAir()) {
            this.getEntityWorld().createExplosion(this, DamageUtil.build(this, DamageTypes.MOB_ATTACK), new FakeExplosionBehavior(), this.getPos(), 2, false, World.ExplosionSourceType.NONE);
            this.remove(RemovalReason.DISCARDED);
        }
        LivingEntity target = this.getEntityWorld().getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, null, this.getX(), this.getY(), this.getZ(), new Box(this.getPos().add(1, 1, 1), this.getPos().subtract(1, 1, 1)));
        if (target != null) {
            ServerPlayerEntity attacker = this.getOwner() instanceof ServerPlayerEntity player ? player : null;
            if (target.getMainHandStack().isOf(Items.SHIELD) && target.isUsingItem())
                target.getMainHandStack().damage((int) this.transformDamage(5), target.getRandom(), attacker);
            else if (target.getOffHandStack().isOf(Items.SHIELD) && target.isUsingItem())
                target.getOffHandStack().damage((int) this.transformDamage(5), target.getRandom(), attacker);
            else target.damage(DamageUtil.build(target, DamageTypes.MOB_ATTACK), this.transformDamage(5));
            this.remove(RemovalReason.DISCARDED);
        }
        for (int i = 0; i < 9; i++)
            this.getEntityWorld().addParticle(ParticleTypes.FLAME,
                    RandomHelper.rangeRand(this.getX(), 0.3),
                    RandomHelper.rangeRand(this.getY() + 0.25, 0.3),
                    RandomHelper.rangeRand(this.getZ(), 0.3),
                    this.getVelocity().getX(),
                    this.getVelocity().getY(),
                    this.getVelocity().getZ());
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
