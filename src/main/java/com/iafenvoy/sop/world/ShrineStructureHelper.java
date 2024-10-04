package com.iafenvoy.sop.world;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.mixin.StructureTemplateAccessor;
import com.iafenvoy.sop.registry.SopTags;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Optional;

public class ShrineStructureHelper {
    public static boolean match(BlockPos playerPos, ServerWorld world) {
        Optional<StructureTemplate> optional = world.getServer().getStructureTemplateManager().getTemplate(new Identifier(SongsOfPower.MOD_ID, "shrine"));
        if (optional.isEmpty()) {
            SongsOfPower.LOGGER.error("Cannot get shrine structure file!");
            return false;
        }
        List<StructureTemplate.PalettedBlockInfoList> templates = ((StructureTemplateAccessor) optional.get()).getBlockInfoLists();
        if (templates.isEmpty()) {
            SongsOfPower.LOGGER.error("Wait what? Where is my shrine structure?");
            return false;
        }
        playerPos = playerPos.add(-7, -1, -7);
        List<StructureTemplate.StructureBlockInfo> blocks = templates.get(0).getAll();
        for (StructureTemplate.StructureBlockInfo block : blocks) {
            BlockPos pos = playerPos.add(block.pos());
            if (block.state().isAir()) {
                if (world.getBlockState(pos).isFullCube(world, pos)) return false;
            } else if (block.state().isOf(Blocks.STONE_BRICKS)) {
                if (!world.getBlockState(pos).isIn(SopTags.STONE_BRICKS)) return false;
            } else if (block.state().isOf(Blocks.STONE_BRICK_STAIRS)) {
                if (!world.getBlockState(pos).isIn(SopTags.STONE_BRICK_STAIRS)) return false;
            } else {
                if (!world.getBlockState(pos).isOf(block.state().getBlock())) return false;
            }
        }
        return true;
    }
}
