package com.iafenvoy.sop.power;

import com.iafenvoy.neptune.util.Timeout;
import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
import net.minecraft.item.ItemStack;

public final class DelaySongPower extends AbstractSongPower<DelaySongPower> {
    private int delay = 0;

    public DelaySongPower(String id, PowerCategory category, ItemStack icon, double mana) {
        this(id, category, icon, data -> mana);
    }

    public DelaySongPower(String id, PowerCategory category, ItemStack icon, Object2DoubleFunction<SongPowerDataHolder> manaSupplier) {
        super(id, category, icon, manaSupplier);
    }

    public DelaySongPower setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    @Override
    protected void applyInternal(SongPowerDataHolder holder) {
        playSound(holder, this.applySound);
        Timeout.create(this.delay, () -> {
            this.apply.accept(holder);
            holder.reduceMana(this.getMana(holder));
            holder.cooldown();
        });
    }

    @Override
    protected PowerType getType() {
        return PowerType.DELAY;
    }

    @Override
    protected DelaySongPower get() {
        return this;
    }
}
