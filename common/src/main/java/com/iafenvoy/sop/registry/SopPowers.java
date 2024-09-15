package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.power.PowerType;
import com.iafenvoy.sop.power.SongPower;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SopPowers {
    //Mobilium
    public static final SongPower MOBILIFLASH = new SongPower("mobiliflash", PowerType.MOBILIUM, new ItemStack(Items.ENDER_PEARL), 10, false)
            .onApply((player, world) -> {

            });
    public static final SongPower MOBILIWINGS = new SongPower("mobiliwings", PowerType.MOBILIUM, new ItemStack(Items.ELYTRA), 1, true)
            .onApply((player, world) -> player.startFallFlying()).onTick((data, player, world) -> {
                if (player.isOnGround() || !player.isFallFlying()) data.disable();
            });
    //Protisium
    public static final SongPower PROTESPHERE = new SongPower("protesphere", PowerType.PROTISIUM, new ItemStack(Items.SHIELD), 1, true);

    public static void init() {
    }
}
