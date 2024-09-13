package com.iafenvoy.sop.fabric.component;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.data.SongPowerData;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class SongPowerComponent implements ComponentV3, AutoSyncedComponent {
    public static final ComponentKey<SongPowerComponent> FRACTION_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(Identifier.of(SongsOfPower.MOD_ID, "songs_power"), SongPowerComponent.class);

    private final LivingEntity entity;
    private final SongPowerData data;

    public SongPowerComponent(LivingEntity entity) {
        this.entity = entity;
        this.data = new SongPowerData();
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public SongPowerData getData() {
        return this.data;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.data.decode(tag);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        this.data.encode(tag);
    }
}
