package com.iafenvoy.sop.power;

import com.iafenvoy.neptune.object.SoundUtil;
import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.item.block.AbstractSongCubeBlock;
import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
import it.unimi.dsi.fastutil.objects.Object2IntFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public sealed abstract class AbstractSongPower<T extends AbstractSongPower<T>> permits DelaySongPower, DummySongPower, InstantSongPower, IntervalSongPower, PersistSongPower {
    public static final List<AbstractSongPower<?>> POWERS = new ArrayList<>();
    public static final Map<String, AbstractSongPower<?>> BY_ID = new HashMap<>();
    private final String id;
    private final PowerCategory category;
    private Object2IntFunction<SongPowerDataHolder> primaryCooldownSupplier = data -> 0, secondaryCooldownSupplier = data -> 0;
    private Object2FloatFunction<SongPowerDataHolder> exhaustion = data -> 0;
    protected Consumer<SongPowerDataHolder> apply = holder -> {
    };
    @Nullable
    protected SoundEvent applySound;
    private boolean experimental = false;

    public AbstractSongPower(String id, PowerCategory category) {
        this.id = id;
        this.category = category;
        if (category != null) {
            POWERS.add(this);
            BY_ID.put(id, this);
            category.registerPower(this);
        }
    }

    public ItemStack getStack() {
        ItemStack stack = new ItemStack(AbstractSongCubeBlock.SONGS.getOrDefault(this.category, Items.AIR));
        stack.getOrCreateNbt().putString(AbstractSongCubeBlock.POWER_TYPE_KEY, this.id);
        return stack;
    }

    public String getId() {
        return this.id;
    }

    public PowerCategory getCategory() {
        return this.category;
    }

    public Identifier getIconTexture() {
        if (this.isEmpty()) return new Identifier("textures/item/barrier.png");
        return new Identifier(SongsOfPower.MOD_ID, "textures/power/" + this.id + ".png");
    }

    public T onApply(Consumer<SongPowerDataHolder> apply) {
        this.apply = apply;
        return this.get();
    }

    public T setApplySound(@Nullable SoundEvent applySound) {
        this.applySound = applySound;
        return this.get();
    }

    public int getPrimaryCooldown(SongPowerData.SinglePowerData data) {
        return this.getPrimaryCooldown(new SongPowerDataHolder(data));
    }

    public int getPrimaryCooldown(SongPowerDataHolder data) {
        return this.primaryCooldownSupplier.applyAsInt(data);
    }

    public T setPrimaryCooldown(int ticks) {
        return this.setPrimaryCooldown(data -> ticks);
    }

    public T setPrimaryCooldown(Object2IntFunction<SongPowerDataHolder> supplier) {
        this.primaryCooldownSupplier = supplier;
        return this.get();
    }

    public int getSecondaryCooldown(SongPowerData.SinglePowerData data) {
        return this.getSecondaryCooldown(new SongPowerDataHolder(data));
    }

    public int getSecondaryCooldown(SongPowerDataHolder data) {
        return this.secondaryCooldownSupplier.applyAsInt(data);
    }

    public T setSecondaryCooldown(int ticks) {
        return this.setSecondaryCooldown(data -> ticks);
    }

    public T setSecondaryCooldown(Object2IntFunction<SongPowerDataHolder> supplier) {
        this.secondaryCooldownSupplier = supplier;
        return this.get();
    }

    public float getExhaustion(SongPowerData.SinglePowerData data) {
        return this.getExhaustion(new SongPowerDataHolder(data));
    }

    public float getExhaustion(SongPowerDataHolder data) {
        return this.exhaustion.apply(data);
    }

    public T setExhaustion(float exhaustion) {
        return this.setExhaustion(data -> exhaustion);
    }

    public T setExhaustion(Object2FloatFunction<SongPowerDataHolder> exhaustion) {
        this.exhaustion = exhaustion;
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

    public T experimental() {
        this.experimental = true;
        return this.get();
    }

    public boolean isExperimental() {
        return this.experimental;
    }

    protected enum PowerType {
        INSTANT, INTERVAL, PERSIST, DELAY, DUMMY;
    }
}
