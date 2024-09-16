package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.item.SongCubeItem;
import com.iafenvoy.sop.power.PowerType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class SopItems {
    //Song Cubes
    public static final SongCubeItem AGGRESSIUM_SONG = registerSongCube("aggressium_song", new SongCubeItem(PowerType.AGGRESSIUM));
    public static final SongCubeItem MOBILIUM_SONG = registerSongCube("mobilium_song", new SongCubeItem(PowerType.MOBILIUM));
    public static final SongCubeItem PROTISIUM_SONG = registerSongCube("protisium_song", new SongCubeItem(PowerType.PROTISIUM));
    public static final SongCubeItem SUPPORTIUM_SONG = registerSongCube("supportium_song", new SongCubeItem(PowerType.SUPPORTIUM));

    private static <T extends Item> T registerSongCube(String id, T item) {
        return Registry.register(Registries.ITEM, new Identifier(SongsOfPower.MOD_ID, id), item);
    }

    public static void init() {
    }
}
