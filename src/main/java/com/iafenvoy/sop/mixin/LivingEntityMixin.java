package com.iafenvoy.sop.mixin;

import com.iafenvoy.sop.power.PowerCategory;
import com.iafenvoy.sop.power.SongPowerData;
import com.iafenvoy.sop.registry.SopPowers;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tickFallFlying", at = @At("HEAD"), cancellable = true)
    private void handleFallFlyingCheck(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!entity.getWorld().isClient && entity instanceof PlayerEntity player && !player.isOnGround() && !player.hasVehicle() && !player.hasStatusEffect(StatusEffects.LEVITATION))
            if (SongPowerData.byPlayer(player).powerEnabled(PowerCategory.MOBILIUM, SopPowers.MOBILIWINGS)) {
                this.setFlag(7, true);
                ci.cancel();
            }
    }

    @SuppressWarnings("all")
    @ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z", ordinal = 0))
    private boolean handleSlideSpeed(boolean original) {
        if ((Object) this instanceof PlayerEntity player && SongPowerData.byPlayer(player).powerEnabled(PowerCategory.MOBILIUM, SopPowers.MOBILIGLIDE)) {
            this.fallDistance = 0;
            return true;
        }
        return original;
    }
}
