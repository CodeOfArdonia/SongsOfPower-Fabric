package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.power.PowerType;
import com.iafenvoy.sop.power.SongPower;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.event.GameEvent;

public class SopPowers {
    //Mobilium
    public static final SongPower MOBILIWINGS = new SongPower("mobiliwings", PowerType.MOBILIUM, new ItemStack(Items.ELYTRA), 1, true)
            .onApply((player, world) -> player.emitGameEvent(GameEvent.ELYTRA_GLIDE))
            .onTick((data,player,world)->{
                if(!player.isFallFlying()) data.disable();
            });
    //Protisium
    public static final SongPower PROTESPHERE = new SongPower("protesphere", PowerType.PROTISIUM, new ItemStack(Items.SHIELD), 1, true);

    public static void init() {
    }
}
