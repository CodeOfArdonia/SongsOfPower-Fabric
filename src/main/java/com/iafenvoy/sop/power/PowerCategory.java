package com.iafenvoy.sop.power;

import com.iafenvoy.neptune.util.Color4i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PowerCategory {
    AGGRESSIUM("aggressium", new Color4i(237, 28, 36, 255), 1),
    MOBILIUM("mobilium", new Color4i(255, 242, 0, 255), 3),
    PROTISIUM("protisium",new Color4i(115, 251, 253, 255), 0),
    SUPPORTIUM("supportium", new Color4i(117, 249, 77, 255), 2);
    private final String id;
    private final Color4i color;
    private final int colorOffset;
    private final List<AbstractSongPower<?>> powers = new ArrayList<>();
    private final Map<String, AbstractSongPower<?>> byId = new HashMap<>();

    PowerCategory(String id, Color4i color, int colorOffset) {
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

    public Color4i getColor() {
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
