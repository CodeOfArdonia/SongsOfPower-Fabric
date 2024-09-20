package com.iafenvoy.sop.power;

import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public final class PersistSongPower extends AbstractSongPower<PersistSongPower> {
    private Consumer<SongPowerDataHolder> unapply = data -> {
    }, tick = data -> {
    };
    @Nullable
    private SoundEvent unapplySound;

    public PersistSongPower(String id, PowerCategory category, ItemStack icon, double mana) {
        this(id, category, icon, data -> mana);
    }

    public PersistSongPower(String id, PowerCategory category, ItemStack icon, Object2DoubleFunction<SongPowerDataHolder> manaSupplier) {
        super(id, category, icon, manaSupplier);
    }

    public PersistSongPower setUnapplySound(@Nullable SoundEvent unapplySound) {
        this.unapplySound = unapplySound;
        return this;
    }

    public PersistSongPower onUnapply(Consumer<SongPowerDataHolder> unapply) {
        this.unapply = unapply;
        return this;
    }

    public PersistSongPower onTick(Consumer<SongPowerDataHolder> tick) {
        this.tick = tick;
        return this;
    }

    @Override
    public void unapply(SongPowerData.SinglePowerData data) {
        SongPowerDataHolder holder = new SongPowerDataHolder(data);
        playSound(holder, this.unapplySound);
        this.unapply.accept(holder);
        holder.cooldown();
    }

    public boolean tick(SongPowerData.SinglePowerData data) {
        SongPowerDataHolder holder = new SongPowerDataHolder(data);
        this.tick.accept(holder);
        if(!holder.isCancelled()) holder.reduceMana(this.getMana(holder));
        return holder.isCancelled();
    }

    @Override
    protected void applyInternal(SongPowerDataHolder holder) {
        playSound(holder, this.applySound);
        this.apply.accept(holder);
    }

    @Override
    protected PowerType getType() {
        return PowerType.PERSIST;
    }

    @Override
    protected PersistSongPower get() {
        return this;
    }
}
