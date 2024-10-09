package com.iafenvoy.sop.item.block;

import com.iafenvoy.sop.item.block.entity.ProtisiumSongCubeBlockEntity;
import com.iafenvoy.sop.power.PowerCategory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ProtisiumSongCubeBlock extends AbstractSongCubeBlock {
    public ProtisiumSongCubeBlock() {
        super(PowerCategory.PROTISIUM);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ProtisiumSongCubeBlockEntity(pos, state);
    }
}
