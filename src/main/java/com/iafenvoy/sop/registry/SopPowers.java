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
import net.minecraft.item.Items;
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
    public static final DelaySongPower AGGROSPHERE = new DelaySongPower("aggrosphere", PowerCategory.AGGRESSIUM, new ItemStack(Items.FIRE_CHARGE), holder -> SopConfig.INSTANCE.aggressium.aggrosphereMana.getDoubleValue())
            .setApplySound(SopSounds.AGGROSPHERE)
            .setDelay(6)
            .setCooldown(holder -> SopConfig.INSTANCE.aggressium.aggrosphereCooldown.getIntegerValue())
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
    public static final DelaySongPower AGGROQUAKE = new DelaySongPower("aggroquake", PowerCategory.AGGRESSIUM, new ItemStack(Items.TNT), holder -> SopConfig.INSTANCE.aggressium.aggroquakeMana.getDoubleValue())
            .setApplySound(SopSounds.AGGROQUAKE)
            .setDelay(8)
            .setCooldown(holder -> SopConfig.INSTANCE.aggressium.aggroquakeCooldown.getIntegerValue())
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
    public static final DelaySongPower MOBILIFLASH = new DelaySongPower("mobiliflash", PowerCategory.MOBILIUM, new ItemStack(Items.ENDER_PEARL), holder -> SopConfig.INSTANCE.mobilium.mobiliflashMana.getDoubleValue())
            .setApplySound(SopSounds.MOBILIFLASH)
            .setDelay(20)
            .setCooldown(holder -> SopConfig.INSTANCE.mobilium.mobiliflashCooldown.getIntegerValue())
            .onApply(holder -> {
                World world = holder.getWorld();
                PlayerEntity player = holder.getPlayer();
                final Vec3d dir = SopMath.getRotationVectorUnit(MathHelper.clamp(player.getPitch(), -15, 15), player.getHeadYaw());
                player.setVelocity(dir.multiply(SopConfig.INSTANCE.mobilium.mobiliflashSpeed.getDoubleValue()));
                player.velocityModified = true;
            });
    public static final PersistSongPower MOBILIWINGS = new PersistSongPower("mobiliwings", PowerCategory.MOBILIUM, new ItemStack(Items.ELYTRA), holder -> SopConfig.INSTANCE.mobilium.mobiliwingsMana.getDoubleValue())
            .setApplySound(SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA)
            .onApply(holder -> holder.getPlayer().startFallFlying())
            .onTick(holder -> {
                PlayerEntity player = holder.getPlayer();
                if (player.isOnGround() || player.getAbilities().flying) holder.cancel();
            });
    public static final PersistSongPower MOBILIGLIDE = new PersistSongPower("mobiliglide", PowerCategory.MOBILIUM, new ItemStack(Items.PHANTOM_MEMBRANE), holder -> SopConfig.INSTANCE.mobilium.mobiliglideMana.getDoubleValue());//GRAVITY attribute not available before 1.20.5
    //Protisium
    public static final PersistSongPower PROTESPHERE = new PersistSongPower("protesphere", PowerCategory.PROTISIUM, new ItemStack(Items.NETHERITE_CHESTPLATE), holder -> SopConfig.INSTANCE.protisium.protesphereMana.getDoubleValue())
            .setApplySound(SopSounds.PROTESPHERE)
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
    public static final PersistSongPower PROTEPOINT = new PersistSongPower("protepoint", PowerCategory.PROTISIUM, new ItemStack(Items.SHIELD), holder -> SopConfig.INSTANCE.protisium.protepointMana.getDoubleValue())
            .setApplySound(SopSounds.PROTEPOINT).experimental()
            .onApply(holder -> {
                ItemStack stack = new ItemStack(SopItems.PROTEPOINT_SHIELD);
                holder.getPlayer().setStackInHand(Hand.OFF_HAND, stack);
            }).onTick(holder -> {
                if (!holder.getPlayer().getOffHandStack().isOf(SopItems.PROTEPOINT_SHIELD)) holder.cancel();
            }).onUnapply(holder -> {
                if (holder.getPlayer().getOffHandStack().isOf(SopItems.PROTEPOINT_SHIELD))
                    holder.getPlayer().setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY.copy());
            });
    public static final IntervalSongPower PROTEHEAL = new IntervalSongPower("proteheal", PowerCategory.PROTISIUM, new ItemStack(Items.GOLDEN_APPLE), holder -> SopConfig.INSTANCE.protisium.protehealMana.getDoubleValue())
            .setCooldown(holder -> SopConfig.INSTANCE.protisium.protehealCooldown.getIntegerValue())
            .setInterval(10)
            .setTimes(10)
            .onApply(holder -> {
                PlayerEntity player = holder.getPlayer();
                if (player.getHealth() >= player.getMaxHealth()) {
                    holder.cancel();
                    return;
                }
                player.heal(1);
            });
    //Supportium
    public static final InstantSongPower SUPPOROLIFT = new InstantSongPower("supporolift", PowerCategory.SUPPORTIUM, new ItemStack(Items.STRING), holder -> SopConfig.INSTANCE.supportium.supporoliftMana.getDoubleValue())
            .setCooldown(holder -> SopConfig.INSTANCE.supportium.supporoliftCooldown.getIntegerValue())
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
