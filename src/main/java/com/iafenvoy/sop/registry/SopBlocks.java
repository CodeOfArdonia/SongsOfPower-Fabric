package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.item.block.AggressiumSongCubeBlock;
import com.iafenvoy.sop.item.block.MobiliumSongCubeBlock;
import com.iafenvoy.sop.item.block.ProtisiumSongCubeBlock;
import com.iafenvoy.sop.item.block.SupportiumSongCubeBlock;
import com.iafenvoy.sop.item.block.entity.AggressiumSongCubeBlockEntity;
import com.iafenvoy.sop.item.block.entity.MobiliumSongCubeBlockEntity;
import com.iafenvoy.sop.item.block.entity.ProtisiumSongCubeBlockEntity;
import com.iafenvoy.sop.item.block.entity.SupportiumSongCubeBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class SopBlocks {
    //Block
    public static final AggressiumSongCubeBlock AGGRESSIUM_SONG = register("aggressium_song", new AggressiumSongCubeBlock());
    public static final MobiliumSongCubeBlock MOBILIUM_SONG = register("mobilium_song", new MobiliumSongCubeBlock());
    public static final ProtisiumSongCubeBlock PROTISIUM_SONG = register("protisium_song", new ProtisiumSongCubeBlock());
    public static final SupportiumSongCubeBlock SUPPORTIUM_SONG = register("supportium_song", new SupportiumSongCubeBlock());
    //Block Entity
    public static final BlockEntityType<AggressiumSongCubeBlockEntity> AGGRESSIUM_SONG_TYPE = register("aggressium_song", FabricBlockEntityTypeBuilder.create(AggressiumSongCubeBlockEntity::new, AGGRESSIUM_SONG).build());
    public static final BlockEntityType<MobiliumSongCubeBlockEntity> MOBILIUM_SONG_TYPE = register("mobilium_song", FabricBlockEntityTypeBuilder.create(MobiliumSongCubeBlockEntity::new, MOBILIUM_SONG).build());
    public static final BlockEntityType<ProtisiumSongCubeBlockEntity> PROTISIUM_SONG_TYPE = register("protisium_song", FabricBlockEntityTypeBuilder.create(ProtisiumSongCubeBlockEntity::new, PROTISIUM_SONG).build());
    public static final BlockEntityType<SupportiumSongCubeBlockEntity> SUPPORTIUM_SONG_TYPE = register("supportium_song", FabricBlockEntityTypeBuilder.create(SupportiumSongCubeBlockEntity::new, SUPPORTIUM_SONG).build());

    private static <T extends Block> T register(String id, T block) {
        Registry.register(Registries.BLOCK, new Identifier(SongsOfPower.MOD_ID, id), block);
        Registry.register(Registries.ITEM, new Identifier(SongsOfPower.MOD_ID, id), new BlockItem(block, new Item.Settings().rarity(Rarity.EPIC).maxCount(1)));
        return block;
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> block) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(SongsOfPower.MOD_ID, id), block);
    }

    public static void init() {
    }
}
