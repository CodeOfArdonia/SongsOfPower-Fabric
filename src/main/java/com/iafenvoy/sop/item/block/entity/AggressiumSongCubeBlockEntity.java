package com.iafenvoy.sop.item.block.entity;

import com.iafenvoy.sop.registry.SopBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class AggressiumSongCubeBlockEntity extends AbstractSongCubeBlockEntity {
    public AggressiumSongCubeBlockEntity(BlockPos pos, BlockState state) {
        super(SopBlocks.AGGRESSIUM_SONG_TYPE, pos, state);
    }
}
