package com.iafenvoy.sop.registry;

import com.iafenvoy.neptune.object.DamageUtil;
import com.iafenvoy.sop.Static;
import com.iafenvoy.sop.config.SopConfig;
import com.iafenvoy.sop.entity.AggroSphereEntity;
import com.iafenvoy.sop.power.*;
import com.iafenvoy.sop.util.SopMath;
import com.iafenvoy.sop.util.WorldUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings("unused")
public final class SopPowers {
    //Aggressium
    public static final DelaySongPower AGGROSPHERE = new DelaySongPower("aggrosphere", PowerCategory.AGGRESSIUM)
            .setApplySound(SopSounds.AGGROSPHERE)
            .setDelay(6)
            .setPrimaryCooldown(holder -> SopConfig.INSTANCE.aggressium.aggrospherePrimaryCooldown.getIntegerValue())
            .setSecondaryCooldown(holder -> SopConfig.INSTANCE.aggressium.aggrosphereSecondaryCooldown.getIntegerValue())
            .setExhaustion(holder -> SopConfig.INSTANCE.aggressium.aggrosphereExhaustion.getFloatValue())
            .onApply(holder -> {
                World world = holder.getWorld();
                PlayerEntity player = holder.getPlayer();
                AggroSphereEntity aggroSphere = SopEntities.AGGRO_SPHERE.create(world);
                if (aggroSphere != null) {
                    final Vec3d dir = SopMath.getRotationVectorUnit(player.getPitch(), player.getHeadYaw());
                    aggroSphere.refreshPositionAndAngles(player.getX(), (player.getY() + 1), player.getZ(), 0, 0);
                    aggroSphere.setVelocity(dir.multiply(SopConfig.INSTANCE.aggressium.aggrosphereSpeed.getDoubleValue()));
                    holder.processProjectile(aggroSphere);
                    world.spawnEntity(aggroSphere);
                }
            });
    public static final DelaySongPower AGGROQUAKE = new DelaySongPower("aggroquake", PowerCategory.AGGRESSIUM)
            .setApplySound(SopSounds.AGGROQUAKE)
            .setDelay(8)
            .setPrimaryCooldown(holder -> SopConfig.INSTANCE.aggressium.aggroquakePrimaryCooldown.getIntegerValue())
            .setSecondaryCooldown(holder -> SopConfig.INSTANCE.aggressium.aggroquakeSecondaryCooldown.getIntegerValue())
            .setExhaustion(holder -> SopConfig.INSTANCE.aggressium.aggroquakeExhaustion.getFloatValue())
            .onApply(holder -> {
                PlayerEntity player = holder.getPlayer();
                Vec3d range = new Vec3d(
                        SopConfig.INSTANCE.aggressium.aggroquakeRange.getDoubleValue(),
                        SopConfig.INSTANCE.aggressium.aggroquakeRange.getDoubleValue(),
                        SopConfig.INSTANCE.aggressium.aggroquakeRange.getDoubleValue());
                List<LivingEntity> entities = holder.getWorld().getEntitiesByClass(LivingEntity.class, new Box(player.getPos().add(range), player.getPos().subtract(range)), x -> x != player);
                for (LivingEntity living : entities) {
                    Vec3d dir = player.getPos().subtract(living.getPos()).multiply(-0.5);
                    living.damage(DamageUtil.build(living, DamageTypes.MOB_ATTACK), SopConfig.INSTANCE.aggressium.aggroquakeDamage.getFloatValue());
                    living.setVelocity(dir.add(0, 0.5, 0));
                    living.velocityModified = true;
                }
            });
    //Mobilium
    public static final DelaySongPower MOBILIFLASH = new DelaySongPower("mobiliflash", PowerCategory.MOBILIUM)
            .setApplySound(SopSounds.MOBILIFLASH)
            .setDelay(20)
            .setPrimaryCooldown(holder -> SopConfig.INSTANCE.mobilium.mobiliflashPrimaryCooldown.getIntegerValue())
            .setSecondaryCooldown(holder -> SopConfig.INSTANCE.mobilium.mobiliflashSecondaryCooldown.getIntegerValue())
            .setExhaustion(holder -> SopConfig.INSTANCE.mobilium.mobiliflashExhaustion.getFloatValue())
            .onApply(holder -> {
                World world = holder.getWorld();
                PlayerEntity player = holder.getPlayer();
                final Vec3d dir = SopMath.getRotationVectorUnit(MathHelper.clamp(player.getPitch(), -15, 15), player.getHeadYaw());
                player.setVelocity(dir.multiply(SopConfig.INSTANCE.mobilium.mobiliflashSpeed.getDoubleValue()));
                player.velocityModified = true;
            });
    public static final PersistSongPower MOBILIWINGS = new PersistSongPower("mobiliwings", PowerCategory.MOBILIUM)
            .setApplySound(SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA)
            .setExhaustion(holder -> SopConfig.INSTANCE.mobilium.mobiliwingsExhaustion.getFloatValue())
            .onApply(holder -> holder.getPlayer().startFallFlying())
            .onTick(holder -> {
                PlayerEntity player = holder.getPlayer();
                if (player.isOnGround() || player.getAbilities().flying) holder.cancel();
            });
    public static final PersistSongPower MOBILIGLIDE = new PersistSongPower("mobiliglide", PowerCategory.MOBILIUM)
            .setExhaustion(holder -> SopConfig.INSTANCE.mobilium.mobiliglideExhaustion.getFloatValue())
            .onApply(holder -> {//GRAVITY attribute not available before 1.20.5
                EntityAttributeInstance instance = holder.getPlayer().getAttributes().getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                if (instance != null)
                    instance.addTemporaryModifier(new EntityAttributeModifier(Static.MOBILIGLIDE_UUID, "protesphere", 1, EntityAttributeModifier.Operation.ADDITION));
            })
            .onTick(holder -> {
                if (holder.getPlayer().isOnGround() || holder.getPlayer().getAbilities().flying) holder.cancel();
            })
            .onUnapply(holder -> {
                EntityAttributeInstance instance = holder.getPlayer().getAttributes().getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                if (instance != null) instance.removeModifier(Static.MOBILIGLIDE_UUID);
            });
    //Protisium
    public static final PersistSongPower PROTESPHERE = new PersistSongPower("protesphere", PowerCategory.PROTISIUM)
            .setApplySound(SopSounds.PROTESPHERE)
            .setExhaustion(holder -> SopConfig.INSTANCE.protisium.protesphereExhaustion.getFloatValue())
            .onApply(holder -> {
                EntityAttributeInstance instance = holder.getPlayer().getAttributes().getCustomInstance(EntityAttributes.GENERIC_ARMOR);
                if (instance != null)
                    instance.addTemporaryModifier(new EntityAttributeModifier(Static.PROTESPHERE_UUID, "protesphere", 50, EntityAttributeModifier.Operation.ADDITION));
            })
            .setUnapplySound(SopSounds.PROTESPHERE_UNAPPLY)
            .onUnapply(holder -> {
                EntityAttributeInstance instance = holder.getPlayer().getAttributes().getCustomInstance(EntityAttributes.GENERIC_ARMOR);
                if (instance != null) instance.removeModifier(Static.PROTESPHERE_UUID);
            });
    public static final PersistSongPower PROTEPOINT = new PersistSongPower("protepoint", PowerCategory.PROTISIUM).experimental()
            .setApplySound(SopSounds.PROTEPOINT)
            .setExhaustion(holder -> SopConfig.INSTANCE.protisium.protepointExhaustion.getFloatValue())
            .onApply(holder -> {
                ItemStack stack = new ItemStack(SopItems.PROTEPOINT_SHIELD);
                holder.getPlayer().setStackInHand(Hand.OFF_HAND, stack);
            }).onTick(holder -> {
                if (!holder.getPlayer().getOffHandStack().isOf(SopItems.PROTEPOINT_SHIELD)) holder.cancel();
            }).onUnapply(holder -> {
                if (holder.getPlayer().getOffHandStack().isOf(SopItems.PROTEPOINT_SHIELD))
                    holder.getPlayer().setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY.copy());
            });
    public static final IntervalSongPower PROTEHEAL = new IntervalSongPower("proteheal", PowerCategory.PROTISIUM)
            .setInterval(10)
            .setTimes(10)
            .setPrimaryCooldown(holder -> SopConfig.INSTANCE.protisium.protehealPrimaryCooldown.getIntegerValue())
            .setSecondaryCooldown(holder -> SopConfig.INSTANCE.protisium.protehealSecondaryCooldown.getIntegerValue())
            .setExhaustion(holder -> SopConfig.INSTANCE.protisium.protehealExhaustion.getFloatValue())
            .onApply(holder -> {
                PlayerEntity player = holder.getPlayer();
                if (player.getHealth() >= player.getMaxHealth()) {
                    holder.cancel();
                    return;
                }
                player.heal(1);
            });
    //Supportium
    public static final InstantSongPower SUPPOROLIFT = new InstantSongPower("supporolift", PowerCategory.SUPPORTIUM)
            .setPrimaryCooldown(holder -> SopConfig.INSTANCE.supportium.supporoliftPrimaryCooldown.getIntegerValue())
            .setSecondaryCooldown(holder -> SopConfig.INSTANCE.supportium.supporoliftSecondaryCooldown.getIntegerValue())
            .setExhaustion(holder -> SopConfig.INSTANCE.supportium.supporoliftExhaustion.getFloatValue())
            .onApply(holder -> {
                PlayerEntity player = holder.getPlayer();
                EntityHitResult result = WorldUtil.raycastEntity(player, SopConfig.INSTANCE.supportium.supporoliftRange.getDoubleValue());
                if (result != null && result.getEntity() instanceof LivingEntity living) {
                    Vec3d dir = player.getPos().subtract(living.getPos()).multiply(0.2);
                    living.setVelocity(dir.add(0, 0.3, 0));
                    living.velocityModified = true;
                } else holder.cancel();
            });

    public static void init() {
    }
}
