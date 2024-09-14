package com.iafenvoy.sop.power;

import com.iafenvoy.neptune.util.function.consumer.Consumer2;
import com.iafenvoy.neptune.util.function.consumer.Consumer3;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Objects;

public final class SongPower {
    public static final SongPower EMPTY = new SongPower("", null, ItemStack.EMPTY, 0);
    private final String id;
    private final PowerType category;
    private final ItemStack icon;
    private final double mana;
    private final boolean persist;
    private Consumer2<PlayerEntity, World> apply = (player, world) -> {
    };
    private Consumer2<PlayerEntity, World> unapply = (player, world) -> {
    };
    private Consumer3<SongPowerData.SinglePowerData, PlayerEntity, World> tick = (data, player, world) -> {
    };

    public SongPower(String id, PowerType category, ItemStack icon, double mana, boolean persist) {
        this.id = id;
        this.category = category;
        this.icon = icon;
        this.mana = mana;
        this.persist = persist;
        if (category != null)
            category.registerPower(this);
    }

    public SongPower(String id, PowerType category, ItemStack icon, double mana) {
        this(id, category, icon, mana, false);
    }

    public SongPower onApply(Consumer2<PlayerEntity, World> consumer) {
        this.apply = consumer;
        return this;
    }

    public SongPower onUnapply(Consumer2<PlayerEntity, World> consumer) {
        this.unapply = consumer;
        return this;
    }

    public SongPower onTick(Consumer3<SongPowerData.SinglePowerData, PlayerEntity, World> consumer) {
        this.tick = consumer;
        return this;
    }

    public boolean isEmpty() {
        return this == EMPTY || this.id.isEmpty();
    }

    public String id() {
        return id;
    }

    public PowerType category() {
        return category;
    }

    public ItemStack icon() {
        return icon;
    }

    public double mana() {
        return mana;
    }

    public boolean persist() {
        return persist;
    }

    public void apply(PlayerEntity player, World world) {
        this.apply.accept(player, world);
    }

    public void unapply(PlayerEntity player, World world) {
        this.unapply.accept(player, world);
    }

    public void tick(SongPowerData.SinglePowerData data, PlayerEntity player, World world) {
        this.tick.accept(data, player, world);
    }
}
