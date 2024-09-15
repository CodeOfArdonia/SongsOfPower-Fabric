package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.item.SongCubeItem;
import com.iafenvoy.sop.power.PowerType;
import com.iafenvoy.sop.power.SongPower;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Supplier;

public class SopItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(SongsOfPower.MOD_ID, RegistryKeys.ITEM);

    //Song Cubes
    public static final RegistrySupplier<SongCubeItem> AGGRESSIUM_SONG = registerSongCube("aggressium_song", () -> new SongCubeItem(PowerType.AGGRESSIUM));
    public static final RegistrySupplier<SongCubeItem> MOBILIUM_SONG = registerSongCube("mobilium_song", () -> new SongCubeItem(PowerType.MOBILIUM));
    public static final RegistrySupplier<SongCubeItem> PROTISIUM_SONG = registerSongCube("protisium_song", () -> new SongCubeItem(PowerType.PROTISIUM));
    public static final RegistrySupplier<SongCubeItem> SUPPORTIUM_SONG = registerSongCube("supportium_song", () -> new SongCubeItem(PowerType.SUPPORTIUM));

    private static <T extends Item> RegistrySupplier<T> registerSongCube(String id, Supplier<T> item) {
        return REGISTRY.register(id, item);
    }

    public static void init() {
        for (SongPower power : SongPower.POWERS)
            CreativeTabRegistry.appendStack(SopItemGroups.MAIN, power.getStack());
    }
}
