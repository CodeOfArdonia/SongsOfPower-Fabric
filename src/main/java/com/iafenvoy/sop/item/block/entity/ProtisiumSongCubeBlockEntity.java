package com.iafenvoy.sop.item.block.entity;

import com.iafenvoy.sop.registry.SopBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class ProtisiumSongCubeBlockEntity extends AbstractSongCubeBlockEntity {
    public ProtisiumSongCubeBlockEntity(BlockPos pos, BlockState state) {
        super(SopBlocks.PROTISIUM_SONG_TYPE, pos, state);
    }
}
