package com.iafenvoy.sop.power;

import com.iafenvoy.neptune.util.function.consumer.Consumer2;
import com.iafenvoy.neptune.util.function.consumer.Consumer3;
import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.item.SongCubeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public final class SongPower {
    public static final List<SongPower> POWERS = new ArrayList<>();
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
        if (category != null) {
            POWERS.add(this);
            category.registerPower(this);
        }
    }

    public SongPower(String id, PowerType category, ItemStack icon, double mana) {
        this(id, category, icon, mana, false);
    }

    public ItemStack getStack() {
        ItemStack stack = new ItemStack(SongCubeItem.SONGS.getOrDefault(this.category, Items.AIR));
        stack.getOrCreateNbt().putString(SongCubeItem.POWER_TYPE_KEY, this.id);
        return stack;
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

    public String getTranslateKey() {
        return "power." + SongsOfPower.MOD_ID + "." + this.id;
    }
}
