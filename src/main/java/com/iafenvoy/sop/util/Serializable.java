package com.iafenvoy.sop.util;

import net.minecraft.nbt.NbtCompound;

public interface Serializable {
    void encode(NbtCompound tag);

    void decode(NbtCompound tag);

    default NbtCompound encode() {
        NbtCompound compound = new NbtCompound();
        this.encode(compound);
        return compound;
    }
}
