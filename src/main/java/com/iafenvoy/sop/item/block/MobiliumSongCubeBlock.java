package com.iafenvoy.sop.item.block;

import com.iafenvoy.sop.item.block.entity.MobiliumSongCubeBlockEntity;
import com.iafenvoy.sop.power.PowerCategory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MobiliumSongCubeBlock extends AbstractSongCubeBlock {
    public MobiliumSongCubeBlock() {
        super(PowerCategory.MOBILIUM);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MobiliumSongCubeBlockEntity(pos, state);
    }
}
