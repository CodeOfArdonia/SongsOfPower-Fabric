package com.iafenvoy.sop.item.block.entity;

import com.iafenvoy.sop.power.AbstractSongPower;
import com.iafenvoy.sop.power.DummySongPower;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public abstract class AbstractSongCubeBlockEntity extends BlockEntity {
    private AbstractSongPower<?> power;

    public AbstractSongCubeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.power = AbstractSongPower.BY_ID.getOrDefault(nbt.getString("songPower"), DummySongPower.EMPTY);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.power != null)
            nbt.putString("songPower", this.power.getId());
    }

    public void setPower(AbstractSongPower<?> power) {
        this.power = power;
    }

    public AbstractSongPower<?> getPower() {
        return this.power;
    }
}
