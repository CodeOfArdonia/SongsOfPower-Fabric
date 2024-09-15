package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class SopItemGroups {
    public static final DeferredRegister<ItemGroup> REGISTRY = DeferredRegister.create(SongsOfPower.MOD_ID, RegistryKeys.ITEM_GROUP);

    public static final RegistrySupplier<ItemGroup> MAIN = register("main", () -> CreativeTabRegistry.create(Text.translatable("itemGroup." + SongsOfPower.MOD_ID + ".main"), () -> new ItemStack(SopItems.AGGRESSIUM_SONG.get())));

    private static <T extends ItemGroup> RegistrySupplier<T> register(String id, Supplier<T> obj) {
        return REGISTRY.register(id, obj);
    }
}
