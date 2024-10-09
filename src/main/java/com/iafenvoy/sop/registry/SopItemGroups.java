package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.power.AbstractSongPower;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class SopItemGroups {
    public static final ItemGroup MAIN = register("main", FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + SongsOfPower.MOD_ID + ".main"))
            .icon(() -> new ItemStack(SopBlocks.AGGRESSIUM_SONG))
            .entries((displayContext, entries) -> {
                entries.add(SopItems.SHRINE_DEBUG);
                entries.add(SopBlocks.AGGRESSIUM_SONG);
                entries.add(SopBlocks.MOBILIUM_SONG);
                entries.add(SopBlocks.PROTISIUM_SONG);
                entries.add(SopBlocks.SUPPORTIUM_SONG);
                for (AbstractSongPower<?> power : AbstractSongPower.POWERS)
                    entries.add(power.getStack());
            }).build());

    private static ItemGroup register(String id, ItemGroup obj) {
        return Registry.register(Registries.ITEM_GROUP, new Identifier(SongsOfPower.MOD_ID, id), obj);
    }

    public static void init() {
    }
}
