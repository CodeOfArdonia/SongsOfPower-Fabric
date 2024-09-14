package com.iafenvoy.sop.util;

import net.minecraft.entity.player.PlayerEntity;

public interface Tickable {
    void tick(PlayerEntity player);
}
