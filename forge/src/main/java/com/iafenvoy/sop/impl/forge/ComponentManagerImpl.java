package com.iafenvoy.sop.impl.forge;

import com.iafenvoy.sop.data.SongPowerData;
import com.iafenvoy.sop.forge.component.SongPowerDataProvider;
import com.iafenvoy.sop.forge.component.SongPowerDataStorage;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
public class ComponentManagerImpl {
    @Nullable
    public static SongPowerData getSongPowerData(LivingEntity entity) {
        LazyOptional<SongPowerDataStorage> storageLazyOptional = entity.getCapability(SongPowerDataProvider.CAPABILITY);
        if (!storageLazyOptional.isPresent()) return null;
        Optional<SongPowerDataStorage> storage = storageLazyOptional.resolve();
        return storage.map(SongPowerDataStorage::getPlayerData).orElse(null);
    }
}
