package com.iafenvoy.sop.impl.fabric;

import com.iafenvoy.sop.data.SongPowerData;
import com.iafenvoy.sop.fabric.component.SongPowerComponent;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
public class ComponentManagerImpl {
    @Nullable
    public static SongPowerData getSongPowerData(LivingEntity entity) {
        Optional<SongPowerComponent> data = SongPowerComponent.FRACTION_COMPONENT.maybeGet(entity);
        return data.map(SongPowerComponent::getData).orElse(null);
    }
}
