package com.iafenvoy.sop.fabric.component;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.power.SongPowerData;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class SongPowerComponent implements ComponentV3, AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<SongPowerComponent> SONG_POWER_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(Identifier.of(SongsOfPower.MOD_ID, "song_power"), SongPowerComponent.class);

    private final PlayerEntity entity;
    private final SongPowerData data;

    public SongPowerComponent(PlayerEntity entity) {
        this.entity = entity;
        this.data = new SongPowerData(entity);
    }

    public PlayerEntity getEntity() {
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

    @Override
    public void tick() {
        this.data.tick();
    }
}
