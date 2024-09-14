package com.iafenvoy.sop.forge.component;

import com.iafenvoy.sop.power.SongPowerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public class SongPowerDataStorage implements INBTSerializable<NbtCompound> {
    private final SongPowerData playerData ;

    public SongPowerDataStorage(PlayerEntity player) {
        this.playerData = new SongPowerData(player);
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
