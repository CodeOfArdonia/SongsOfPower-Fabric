package com.iafenvoy.sop.data;

import com.iafenvoy.neptune.util.function.consumer.Consumer2;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public record SongPower(String id, PowerType category, ItemStack icon, double mana, boolean persist,
                        Consumer2<PlayerEntity, World> apply, Consumer2<PlayerEntity, World> unapply) {
    public static final SongPower EMPTY = new SongPower("", null, ItemStack.EMPTY, 0, (player, world) -> {
    });

    public SongPower(String id, PowerType category, ItemStack icon, double mana, boolean persist, Consumer2<PlayerEntity, World> apply, Consumer2<PlayerEntity, World> unapply) {
        this.id = id;
        this.category = category;
        this.icon = icon;
        this.mana = mana;
        this.persist = persist;
        this.apply = apply;
        this.unapply = unapply;
        if (category != null)
            category.registerPower(this);
    }

    public SongPower(String id, PowerType category, ItemStack icon, double mana, Consumer2<PlayerEntity, World> apply) {
        this(id, category, icon, mana, false, apply, (player, world) -> {
        });
    }

    public boolean isEmpty() {
        return this == EMPTY || this.id.isEmpty();
    }
}
