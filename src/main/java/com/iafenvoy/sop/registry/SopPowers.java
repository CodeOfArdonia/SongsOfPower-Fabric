package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.Static;
import com.iafenvoy.sop.entity.AggroSphereEntity;
import com.iafenvoy.sop.power.*;
import com.iafenvoy.sop.util.SopMath;
import com.iafenvoy.sop.util.WorldUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public final class SopPowers {
    //Aggressium
    public static final DelaySongPower AGGROSPHERE = new DelaySongPower("aggrosphere", PowerCategory.AGGRESSIUM, new ItemStack(Items.FIRE_CHARGE), 10)
            .setApplySound(SopSounds.AGGROSPHERE)
            .setDelay(6)
            .setCooldown(10)
            .onApply(holder -> {
                World world = holder.getWorld();
                PlayerEntity player = holder.getPlayer();
                AggroSphereEntity aggroSphere = SopEntities.AGGRO_SPHERE.create(world);
                if (aggroSphere != null) {
                    final int speed = 3;
                    final Vec3d dir = SopMath.getRotationVectorUnit(player.getPitch(), player.getHeadYaw());
                    aggroSphere.refreshPositionAndAngles(player.getX(), (player.getY() + 1), player.getZ(), 0, 0);
                    aggroSphere.setVelocity(dir.multiply(speed));
                    holder.processProjectile(aggroSphere);
                    world.spawnEntity(aggroSphere);
                }
            });
    //Mobilium
    public static final DelaySongPower MOBILIFLASH = new DelaySongPower("mobiliflash", PowerCategory.MOBILIUM, new ItemStack(Items.ENDER_PEARL), 30)
            .setApplySound(SopSounds.MOBILIFLASH)
            .setDelay(20)
            .setCooldown(40)
            .onApply(holder -> {
                World world = holder.getWorld();
                PlayerEntity player = holder.getPlayer();
                final int speed = 8;
                final Vec3d dir = SopMath.getRotationVectorUnit(MathHelper.clamp(player.getPitch(), -15, 15), player.getHeadYaw());
                player.setVelocity(dir.multiply(speed));
                player.velocityModified = true;
            });
    public static final PersistSongPower MOBILIWINGS = new PersistSongPower("mobiliwings", PowerCategory.MOBILIUM, new ItemStack(Items.ELYTRA), 1)
            .setApplySound(SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA)
            .onApply(holder -> holder.getPlayer().startFallFlying())
            .onTick(holder -> {
                PlayerEntity player = holder.getPlayer();
                if (player.isOnGround() || player.getAbilities().flying) holder.cancel();
            });
    public static final PersistSongPower MOBILIGLIDE = new PersistSongPower("mobiliglide", PowerCategory.MOBILIUM, new ItemStack(Items.PHANTOM_MEMBRANE), 1);//GRAVITY attribute not available before 1.20.5
    //Protisium
    public static final PersistSongPower PROTESPHERE = new PersistSongPower("protesphere", PowerCategory.PROTISIUM, new ItemStack(Items.NETHERITE_CHESTPLATE), 2)
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
    public static final PersistSongPower PROTEPOINT = new PersistSongPower("protepoint", PowerCategory.PROTISIUM, new ItemStack(Items.SHIELD), 1)
            .setApplySound(SopSounds.PROTEPOINT).experimental()
            .onApply(holder -> {
                ItemStack stack = new ItemStack(SopItems.PROTEPOINT_SHIELD);
                holder.getPlayer().setStackInHand(Hand.OFF_HAND, stack);
            }).onTick(holder -> {
                if (!holder.getPlayer().getOffHandStack().isOf(SopItems.PROTEPOINT_SHIELD)) holder.getData().disable();
            }).onUnapply(holder -> {
                if (holder.getPlayer().getOffHandStack().isOf(SopItems.PROTEPOINT_SHIELD))
                    holder.getPlayer().setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY.copy());
            });
    public static final IntervalSongPower PROTEHEAL = new IntervalSongPower("proteheal", PowerCategory.PROTISIUM, new ItemStack(Items.GOLDEN_APPLE), 30)
            .setCooldown(200)
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
    public static final InstantSongPower SUPPOROLIFT = new InstantSongPower("supporolift", PowerCategory.SUPPORTIUM, new ItemStack(Items.STRING), 50)
            .setCooldown(200)
            .onApply(holder -> {
                PlayerEntity player = holder.getPlayer();
                EntityHitResult result = WorldUtil.raycastEntity(player, 20);
                if (result != null && result.getEntity() instanceof LivingEntity living) {
                    Vec3d dir = player.getPos().subtract(living.getPos()).multiply(0.2);
                    living.setVelocity(dir.add(0, 0.3, 0));
                    living.velocityModified = true;
                } else holder.cancel();
            });

    public static void init() {
    }
}
