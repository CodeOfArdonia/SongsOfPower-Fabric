package com.iafenvoy.sop.power;

import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PowerCategory {
    AGGRESSIUM("aggressium", Formatting.RED, 1),
    MOBILIUM("mobilium", Formatting.YELLOW, 3),
    PROTISIUM("protisium", Formatting.AQUA, 0),
    SUPPORTIUM("supportium", Formatting.GREEN, 2);
    private final String id;
    private final Formatting color;
    private final int colorOffset;
    private final List<AbstractSongPower<?>> powers = new ArrayList<>();
    private final Map<String, AbstractSongPower<?>> byId = new HashMap<>();

    PowerCategory(String id, Formatting color, int colorOffset) {
        this.id = id;
        this.color = color;
        this.colorOffset = colorOffset;
    }

    public int getColorOffset() {
        return this.colorOffset;
    }

    public String getId() {
        return this.id;
    }

    public Formatting getColor() {
        return this.color;
    }

    public void registerPower(AbstractSongPower<?> power) {
        this.powers.add(power);
        AbstractSongPower<?> p = this.byId.put(power.getId(), power);
        if (p != null)
            throw new IllegalArgumentException("Duplicated id " + p.getId() + " for song power type " + this.id + "!");
    }

    public AbstractSongPower<?> getPowerById(String id) {
        return this.powers.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(DummySongPower.EMPTY);
    }
}
