package com.iafenvoy.sop.impl;

import com.iafenvoy.sop.data.SongPowerData;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.LivingEntity;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

public class ComponentManager {
    @ExpectPlatform
    @Nullable
    public static SongPowerData getSongPowerData(LivingEntity entity) {
        throw new NotImplementedException("This method should be replaced by Architectury.");
    }
}
