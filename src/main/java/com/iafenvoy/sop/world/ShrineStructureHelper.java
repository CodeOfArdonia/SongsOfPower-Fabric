package com.iafenvoy.sop.world;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.mixin.StructureTemplateAccessor;
import com.iafenvoy.sop.registry.SopTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.StairShape;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.List;
import java.util.Optional;

public class ShrineStructureHelper {
    private static List<StructureTemplate.StructureBlockInfo> getBlocks(ServerWorld world) {
        Optional<StructureTemplate> optional = world.getServer().getStructureTemplateManager().getTemplate(new Identifier(SongsOfPower.MOD_ID, "shrine"));
        if (optional.isEmpty()) {
            SongsOfPower.LOGGER.error("Cannot get shrine structure file!");
            return List.of();
        }
        List<StructureTemplate.PalettedBlockInfoList> templates = ((StructureTemplateAccessor) optional.get()).getBlockInfoLists();
        if (templates.isEmpty()) {
            SongsOfPower.LOGGER.error("Wait what? Where is my shrine structure?");
            return List.of();
        }
        return templates.get(0).getAll();
    }

    public static boolean match(BlockPos playerPos, ServerWorld world) {
        if(!world.getBlockState(playerPos.down()).isOf(Blocks.CHISELED_STONE_BRICKS)) return false;
        List<StructureTemplate.StructureBlockInfo> blocks = getBlocks(world);
        if (blocks.isEmpty()) return false;
        playerPos = playerPos.add(-7, -1, -7);
        for (StructureTemplate.StructureBlockInfo block : blocks) {
            BlockPos pos = playerPos.add(block.pos());
            BlockState state = world.getBlockState(pos);
            if (block.state().isAir()) {
                if (state.isFullCube(world, pos) && !state.isIn(BlockTags.LEAVES)) return false;
            } else if (block.state().isOf(Blocks.STONE_BRICKS)) {
                if (!state.isIn(SopTags.STONE_BRICKS)) return false;
            } else if (block.state().isOf(Blocks.STONE_BRICK_STAIRS)) {
                if (!state.isIn(SopTags.STONE_BRICK_STAIRS) || !stateEqual(block.state(), state)) return false;
            } else {
                if (!state.isOf(block.state().getBlock())) return false;
            }
        }
        return true;
    }

    public static void generate(BlockPos origin, ServerWorld world) {
        List<StructureTemplate.StructureBlockInfo> blocks = getBlocks(world);
        origin = origin.add(-7, -1, -7);
        for (StructureTemplate.StructureBlockInfo block : blocks) {
            BlockPos pos = origin.add(block.pos());
            world.setBlockState(pos, block.state());
        }
    }

    public static boolean stateEqual(BlockState state1, BlockState state2) {
        if (!(state1.getBlock() instanceof StairsBlock) || !(state2.getBlock() instanceof StairsBlock)) return false;
        if (state1.get(StairsBlock.WATERLOGGED) ^ state2.get(StairsBlock.WATERLOGGED)) return false;
        if (state1.get(StairsBlock.HALF) != state2.get(StairsBlock.HALF)) return false;
        Direction dir1 = state1.get(StairsBlock.FACING), dir2 = state2.get(StairsBlock.FACING);
        StairShape shape1 = state1.get(StairsBlock.SHAPE), shape2 = state2.get(StairsBlock.SHAPE);
        if (shape1 == shape2) return dir1 == dir2;
        if (shape1 == StairShape.STRAIGHT || shape2 == StairShape.STRAIGHT) return false;
        if (shape1 == StairShape.INNER_LEFT) {
            if (shape2 != StairShape.INNER_RIGHT) return false;
            return dir1.rotateYCounterclockwise() == dir2;
        }
        if (shape1 == StairShape.INNER_RIGHT) {
            if (shape2 != StairShape.INNER_LEFT) return false;
            return dir1.rotateYClockwise() == dir2;
        }
        if (shape1 == StairShape.OUTER_LEFT) {
            if (shape2 != StairShape.OUTER_RIGHT) return false;
            return dir1.rotateYCounterclockwise() == dir2;
        }
        if (shape1 == StairShape.OUTER_RIGHT) {
            if (shape2 != StairShape.OUTER_LEFT) return false;
            return dir1.rotateYClockwise() == dir2;
        }
        return false;
    }
}
