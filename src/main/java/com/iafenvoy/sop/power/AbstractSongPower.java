package com.iafenvoy.sop.power;

import com.iafenvoy.neptune.object.SoundUtil;
import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.item.SongCubeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public sealed abstract class AbstractSongPower<T extends AbstractSongPower<T>> permits DelaySongPower, DummySongPower, InstantSongPower, PersistSongPower {
    public static final List<AbstractSongPower<?>> POWERS = new ArrayList<>();
    private final String id;
    private final PowerCategory category;
    private final ItemStack icon;
    private final ManaSupplier manaSupplier;
    private CooldownSupplier cooldownSupplier;
    protected Consumer<SongPowerDataHolder> apply = holder -> {
    };
    @Nullable
    protected SoundEvent applySound;

    public AbstractSongPower(String id, PowerCategory category, ItemStack icon, ManaSupplier manaSupplier) {
        this.id = id;
        this.category = category;
        this.icon = icon;
        this.manaSupplier = manaSupplier;
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

    public String getId() {
        return this.id;
    }

    public PowerCategory getCategory() {
        return this.category;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public T onApply(Consumer<SongPowerDataHolder> apply) {
        this.apply = apply;
        return this.get();
    }

    public T setApplySound(@Nullable SoundEvent applySound) {
        this.applySound = applySound;
        return this.get();
    }

    public double getMana(SongPowerDataHolder data) {
        return this.manaSupplier.getMana(data);
    }

    public int getCooldown(SongPowerData.SinglePowerData data) {
        return this.getCooldown(new SongPowerDataHolder(data));
    }

    public int getCooldown(SongPowerDataHolder data) {
        return this.cooldownSupplier.getCooldown(data);
    }

    public T setCooldown(int ticks) {
        return this.setCooldown(data -> ticks);
    }

    public T setCooldown(CooldownSupplier supplier) {
        this.cooldownSupplier = supplier;
        return this.get();
    }

    public boolean isPersist() {
        return this.getType() == PowerType.PERSIST;
    }

    public boolean cancellable() {
        return this.getType() == PowerType.INSTANT;
    }

    public void apply(SongPowerData.SinglePowerData data) {
        this.applyInternal(new SongPowerDataHolder(data));
    }

    public void unapply(SongPowerData.SinglePowerData data) {
    }

    public boolean isEmpty() {
        return this == DummySongPower.EMPTY || this.id.isEmpty();
    }

    public String getTranslateKey() {
        return "power." + SongsOfPower.MOD_ID + "." + this.id;
    }

    protected abstract void applyInternal(SongPowerDataHolder holder);

    protected abstract PowerType getType();

    protected abstract T get();

    protected static void playSound(SongPowerDataHolder holder, @Nullable SoundEvent sound) {
        if (sound != null)
            SoundUtil.playSound(holder.getWorld(), holder.getPlayer().getX(), holder.getPlayer().getY(), holder.getPlayer().getZ(), sound.getId(), 0.5f, 1);
    }

    @FunctionalInterface
    public interface ManaSupplier {
        double getMana(SongPowerDataHolder data);
    }

    @FunctionalInterface
    public interface CooldownSupplier {
        int getCooldown(SongPowerDataHolder data);
    }

    protected enum PowerType {
        INSTANT, PERSIST, DELAY, DUMMY;
    }
}
