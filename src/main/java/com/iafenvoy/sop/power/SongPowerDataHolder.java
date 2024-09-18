package com.iafenvoy.sop.power;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SongPowerDataHolder {
    private final SongPowerData.SinglePowerData data;
    private boolean cancelled = false;

    public SongPowerDataHolder(SongPowerData.SinglePowerData data) {
        this.data = data;
    }

    public SongPowerData.SinglePowerData getData() {
        return this.data;
    }

    public PlayerEntity getPlayer() {
        return this.data.getPlayer();
    }

    public World getWorld() {
        return this.data.getPlayer().getEntityWorld();
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void reduceMana(double amount) {
        this.data.reduceMana(amount);
    }

    public void cooldown() {
        this.data.cooldown();
    }
}
