package com.iafenvoy.sop.registry;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.item.ProtepointShieldItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class SopItems {
    //Fake Item, should not use in game without song power.
    public static final FabricShieldItem PROTEPOINT_SHIELD = register("protepoint_shield", new ProtepointShieldItem());

    private static <T extends Item> T register(String id, T item) {
        return Registry.register(Registries.ITEM, new Identifier(SongsOfPower.MOD_ID, id), item);
    }

    public static void init() {
    }
}
