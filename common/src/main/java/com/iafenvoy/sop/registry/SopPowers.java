package com.iafenvoy.sop.registry;

import com.iafenvoy.neptune.util.Timeout;
import com.iafenvoy.sop.entity.AggroSphereEntity;
import com.iafenvoy.sop.power.PowerType;
import com.iafenvoy.sop.power.SongPower;
import com.iafenvoy.sop.util.SopMath;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
public class SopPowers {
    //Aggressium
    public static final SongPower AGGROSPHERE = new SongPower("aggrosphere", PowerType.AGGRESSIUM, new ItemStack(Items.FIRE_CHARGE), 10, false)
            .setApplySound(SopSounds.AGGROSPHERE).setApplyDelay(6)
            .onApply((player, world) -> {
                AggroSphereEntity aggroSphere = SopEntities.AGGRO_SPHERE.get().create(world);
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
    public static final SongPower MOBILIFLASH = new SongPower("mobiliflash", PowerType.MOBILIUM, new ItemStack(Items.ENDER_PEARL), 30, false)
            .setApplySound(SopSounds.MOBILIFLASH).setApplyDelay(20)
            .onApply((player, world) -> {
                final int speed = 10;
                final Vec3d dir = SopMath.getRotationVectorUnit(MathHelper.clamp(player.getPitch(), -15, 15), player.getHeadYaw());
                player.setVelocity(dir.multiply(speed));
                player.velocityModified = true;
            });
    public static final SongPower MOBILIWINGS = new SongPower("mobiliwings", PowerType.MOBILIUM, new ItemStack(Items.ELYTRA), 1, true)
            .setApplySound(SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA)
            .onApply((player, world) -> player.startFallFlying()).onTick((data, player, world) -> {
                if (player.isOnGround() || !player.isFallFlying()) data.disable();
            });
    //Protisium
    public static final SongPower PROTESPHERE = new SongPower("protesphere", PowerType.PROTISIUM, new ItemStack(Items.SHIELD), 1, true)
            .onTick((data, player, world) -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, 3)));

    public static void init() {
    }
}
