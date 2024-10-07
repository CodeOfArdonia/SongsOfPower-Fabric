package com.iafenvoy.sop.power;

import com.iafenvoy.neptune.util.Timeout;
import it.unimi.dsi.fastutil.objects.Object2IntFunction;

public final class IntervalSongPower extends AbstractSongPower<IntervalSongPower> {
    private Object2IntFunction<SongPowerDataHolder> times = data -> 0;
    private Object2IntFunction<SongPowerDataHolder> interval = data -> 0;

    public IntervalSongPower(String id, PowerCategory category) {
        super(id, category);
    }

    public IntervalSongPower setInterval(int interval) {
        return this.setInterval(data -> interval);
    }

    public IntervalSongPower setInterval(Object2IntFunction<SongPowerDataHolder> interval) {
        this.interval = interval;
        return this;
    }

    public IntervalSongPower setTimes(int times) {
        return this.setTimes(data -> times);
    }

    public IntervalSongPower setTimes(Object2IntFunction<SongPowerDataHolder> times) {
        this.times = times;
        return this;
    }

    @Override
    protected void applyInternal(SongPowerDataHolder holder) {
        this.apply.accept(holder);
        if(holder.isCancelled()) return;
        Timeout.create(this.interval.applyAsInt(holder), this.times.applyAsInt(holder), () -> this.apply.accept(holder));
    }

    @Override
    protected PowerType getType() {
        return PowerType.INTERVAL;
    }

    @Override
    protected IntervalSongPower get() {
        return this;
    }
}
