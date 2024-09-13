package com.iafenvoy.sop.forge.component;

import com.iafenvoy.sop.data.SongPowerData;
import net.minecraft.nbt.NbtCompound;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public class SongPowerDataStorage implements INBTSerializable<NbtCompound> {
    private final SongPowerData playerData = new SongPowerData();

    public SongPowerDataStorage() {
    }

    @Override
    public NbtCompound serializeNBT() {
        NbtCompound compound = new NbtCompound();
        this.playerData.encode(compound);
        return compound;
    }

    @Override
    public void deserializeNBT(NbtCompound compound) {
        this.playerData.decode(compound);
    }

    public SongPowerData getPlayerData() {
        return this.playerData;
    }
}
