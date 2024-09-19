package com.iafenvoy.sop.registry;

import com.iafenvoy.neptune.event.LivingEntityEvents;
import com.iafenvoy.sop.entity.AggroSphereEntity;
import com.iafenvoy.sop.power.*;
import com.iafenvoy.sop.util.SopMath;
import com.iafenvoy.sop.util.WorldUtil;
import net.minecraft.entity.LivingEntity;
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
                    aggroSphere.setOwner(player);
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
    //Protisium
    public static final PersistSongPower PROTESPHERE = new PersistSongPower("protesphere", PowerCategory.PROTISIUM, new ItemStack(Items.NETHERITE_CHESTPLATE), 2)
            .setApplySound(SopSounds.PROTESPHERE)
            .setUnapplySound(SopSounds.PROTESPHERE_UNAPPLY);//Protect will be handled by event.
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
    //Supportium
    public static final InstantSongPower SUPPOROLIFT = new InstantSongPower("supporolift", PowerCategory.SUPPORTIUM, new ItemStack(Items.STRING), 50)
            .setCooldown(200)
            .onApply(holder -> {
                PlayerEntity player = holder.getPlayer();
                EntityHitResult result = WorldUtil.raycastEntity(player, 20);
                if (result != null && result.getEntity() instanceof LivingEntity living) {
                    Vec3d dir = player.getPos().subtract(living.getPos()).multiply(0.2);
                    living.setVelocity(dir);
                    living.velocityModified = true;
                } else holder.cancel();
            });

    public static void init() {
        LivingEntityEvents.DAMAGE.register((livingEntity, damageSource, v) -> {
            if (livingEntity instanceof PlayerEntity target && SongPowerData.byPlayer(target).powerEnabled(PowerCategory.PROTISIUM, PROTESPHERE))
                v /= 5;
            return v;
        });
    }
}
