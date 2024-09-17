package com.iafenvoy.sop.power;

import com.iafenvoy.neptune.object.SoundUtil;
import com.iafenvoy.neptune.util.Timeout;
import com.iafenvoy.neptune.util.function.consumer.Consumer2;
import com.iafenvoy.neptune.util.function.consumer.Consumer3;
import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.item.SongCubeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

public final class SongPower {
    public static final List<SongPower> POWERS = new ArrayList<>();
    public static final SongPower EMPTY = new SongPower("", null, ItemStack.EMPTY, 0, false);
    private final String id;
    private final PowerType category;
    private final ItemStack icon;
    private final DoubleSupplier mana;
    private final boolean persist;
    private Consumer2<PlayerEntity, World> apply = (player, world) -> {
    }, unapply = (player, world) -> {
    };
    private Consumer3<SongPowerData.SinglePowerData, PlayerEntity, World> tick = (data, player, world) -> {
    };
    private int applyDelayTick = 0;
    @Nullable
    private SoundEvent applySound;
    @Nullable
    private SoundEvent unapplySound;

    public SongPower(String id, PowerType category, ItemStack icon, final double mana, boolean persist) {
        this(id, category, icon, () -> mana, persist);
    }

    public SongPower(String id, PowerType category, ItemStack icon, DoubleSupplier mana, boolean persist) {
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

    public String getId() {
        return this.id;
    }

    public PowerType getCategory() {
        return this.category;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public double getMana() {
        return this.mana.getAsDouble();
    }

    public boolean isPersist() {
        return this.persist;
    }

    public void apply(PlayerEntity player, World world) {
        this.playApplySound(player, world);
        Timeout.create(this.getApplyDelay(), () -> this.apply.accept(player, world));
    }

    public void unapply(PlayerEntity player, World world) {
        this.playUnapplySound(player, world);
        Timeout.create(this.getApplyDelay(), () -> this.unapply.accept(player, world));
    }

    public void tick(SongPowerData.SinglePowerData data, PlayerEntity player, World world) {
        this.tick.accept(data, player, world);
    }

    public SongPower setApplySound(@Nullable SoundEvent applySound) {
        this.applySound = applySound;
        if (this.unapplySound == null) this.setUnapplySound(applySound);
        return this;
    }

    public SongPower setUnapplySound(@Nullable SoundEvent unapplySound) {
        this.unapplySound = unapplySound;
        return this;
    }

    public void playApplySound(PlayerEntity player, World world) {
        if (this.applySound != null)
            SoundUtil.playSound(world, player.getX(), player.getY(), player.getZ(), this.applySound.getId(), 0.5f, 1);
    }

    public void playUnapplySound(PlayerEntity player, World world) {
        if (this.unapplySound != null)
            SoundUtil.playSound(world, player.getX(), player.getY(), player.getZ(), this.unapplySound.getId(), 0.5f, 1);
    }

    public SongPower setApplyDelay(int delay) {
        if (this.persist) throw new IllegalStateException("Delay only available for not persist power");
        this.applyDelayTick = delay;
        return this;
    }

    public int getApplyDelay() {
        return this.applyDelayTick;
    }

    public String getTranslateKey() {
        return "power." + SongsOfPower.MOD_ID + "." + this.id;
    }
}
