package com.iafenvoy.sop.power;

import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
import net.minecraft.item.ItemStack;

public final class InstantSongPower extends AbstractSongPower<InstantSongPower> {
    public InstantSongPower(String id, PowerCategory category, ItemStack icon, double mana) {
        this(id, category, icon, data -> mana);
    }

    public InstantSongPower(String id, PowerCategory category, ItemStack icon, Object2DoubleFunction<SongPowerDataHolder> manaSupplier) {
        super(id, category, icon, manaSupplier);
    }

    @Override
    protected void applyInternal(SongPowerDataHolder holder) {
        this.apply.accept(holder);
        if (!holder.isCancelled()) {
            holder.reduceMana(this.getMana(holder));
            holder.cooldown();
            playSound(holder, this.applySound);
        }
    }

    @Override
    protected PowerType getType() {
        return PowerType.INSTANT;
    }

    @Override
    protected InstantSongPower get() {
        return this;
    }
}
