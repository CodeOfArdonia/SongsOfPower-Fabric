package com.iafenvoy.sop.power;

import net.minecraft.item.ItemStack;

public non-sealed abstract class DummySongPower extends AbstractSongPower<DummySongPower> {
    public static final DummySongPower EMPTY = new DummySongPower("", null, ItemStack.EMPTY, 0) {
        @Override
        protected void applyInternal(SongPowerDataHolder holder) {
        }
    };

    public DummySongPower(String id, PowerCategory category, ItemStack icon, double mana) {
        this(id, category, icon, data -> mana);
    }

    public DummySongPower(String id, PowerCategory category, ItemStack icon, ManaSupplier manaSupplier) {
        super(id, category, icon, manaSupplier);
    }

    @Override
    protected PowerType getType() {
        return PowerType.DUMMY;
    }

    @Override
    protected DummySongPower get() {
        return this;
    }
}
