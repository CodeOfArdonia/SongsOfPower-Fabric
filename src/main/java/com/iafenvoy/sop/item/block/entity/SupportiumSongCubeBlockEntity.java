package com.iafenvoy.sop.item.block.entity;

import com.iafenvoy.sop.registry.SopBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class SupportiumSongCubeBlockEntity extends AbstractSongCubeBlockEntity {
    public SupportiumSongCubeBlockEntity(BlockPos pos, BlockState state) {
        super(SopBlocks.SUPPORTIUM_SONG_TYPE, pos, state);
    }
}
