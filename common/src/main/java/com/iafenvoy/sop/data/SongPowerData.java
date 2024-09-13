package com.iafenvoy.sop.data;

import com.iafenvoy.neptune.fraction.Fraction;
import com.iafenvoy.sop.impl.ComponentManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class SongPowerData {
    private Fraction fraction;
    private boolean abilityEnabled;

    public SongPowerData() {
        this.fraction = Fraction.NONE;
        this.abilityEnabled = false;
    }

    public void encode(NbtCompound tag) {
        tag.putString("fraction", this.fraction.id().toString());
        tag.putBoolean("abilityEnabled", this.abilityEnabled);
    }

    public void decode(NbtCompound tag) {
        this.fraction = Fraction.getById(tag.getString("fraction"));
        this.abilityEnabled = tag.getBoolean("abilityEnabled");
    }

    public Fraction getFraction() {
        return fraction;
    }

    public void setFraction(Fraction fraction) {
        this.fraction = fraction;
    }

    public boolean isAbilityEnabled() {
        return abilityEnabled;
    }

    public void setAbilityEnabled(boolean abilityEnabled) {
        this.abilityEnabled = abilityEnabled;
    }

    public static SongPowerData byPlayer(PlayerEntity player) {
        return ComponentManager.getSongPowerData(player);
    }
}
