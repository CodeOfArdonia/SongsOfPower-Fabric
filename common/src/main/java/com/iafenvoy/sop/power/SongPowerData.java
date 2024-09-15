package com.iafenvoy.sop.power;

import com.iafenvoy.sop.impl.ComponentManager;
import com.iafenvoy.sop.item.SongCubeItem;
import com.iafenvoy.sop.util.Serializable;
import com.iafenvoy.sop.util.Tickable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class SongPowerData implements Serializable, Tickable {
    private final PlayerEntity player;
    private boolean enabled = false;
    private final Map<PowerType, SinglePowerData> byType = new HashMap<>();
    private final SinglePowerData aggressium = new SinglePowerData(this, PowerType.AGGRESSIUM);
    private final SinglePowerData mobilium = new SinglePowerData(this, PowerType.MOBILIUM);
    private final SinglePowerData protisium = new SinglePowerData(this, PowerType.PROTISIUM);
    private final SinglePowerData supportium = new SinglePowerData(this, PowerType.SUPPORTIUM);

    private boolean modified = false;

    public SongPowerData(PlayerEntity player) {
        this.player = player;
    }

    public boolean isModified() {
        return this.modified;
    }

    public void markDirty() {
        this.modified = true;
    }

    public void clean() {
        this.modified = false;
    }

    @Override
    public void encode(NbtCompound tag) {
        tag.putBoolean("enabled", this.enabled);
        tag.put("aggressium", this.aggressium.encode());
        tag.put("mobilium", this.mobilium.encode());
        tag.put("protisium", this.protisium.encode());
        tag.put("supportium", this.supportium.encode());
    }

    @Override
    public void decode(NbtCompound tag) {
        this.enabled = tag.getBoolean("enabled");
        this.aggressium.decode(tag.getCompound("aggressium"));
        this.mobilium.decode(tag.getCompound("mobilium"));
        this.protisium.decode(tag.getCompound("protisium"));
        this.supportium.decode(tag.getCompound("supportium"));
    }

    @Override
    public void tick() {
        this.aggressium.tick();
        this.mobilium.tick();
        this.protisium.tick();
        this.supportium.tick();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.markDirty();
    }

    public void enable() {
        this.setEnabled(true);
    }

    public void disable() {
        this.setEnabled(false);
    }

    public SinglePowerData get(PowerType type) {
        return this.byType.get(type);
    }

    public SinglePowerData getAggressium() {
        return this.aggressium;
    }

    public SinglePowerData getMobilium() {
        return this.mobilium;
    }

    public SinglePowerData getProtisium() {
        return this.protisium;
    }

    public SinglePowerData getSupportium() {
        return this.supportium;
    }

    public boolean powerEnabled(PowerType type, SongPower power) {
        SinglePowerData data = this.get(type);
        return data.hasPower() && data.isEnabled() && data.getActivePower() == power;
    }

    public static SongPowerData byPlayer(PlayerEntity player) {
        return ComponentManager.getSongPowerData(player);
    }

    public static class SinglePowerData implements Serializable, Tickable {
        private final SongPowerData parent;
        private final PowerType type;
        private SongPower activePower = SongPower.EMPTY;
        private boolean enabled = false;
        private double maxMana = 100;
        private double remainMana = 100;
        private double recoverMana = 10;
        private ItemStack holdItem = ItemStack.EMPTY;

        public SinglePowerData(SongPowerData parent, PowerType type) {
            this.parent = parent;
            this.type = type;
            this.parent.byType.put(type, this);
        }

        @Override
        public void encode(NbtCompound tag) {
            tag.putBoolean("enabled", this.enabled);
            tag.putDouble("maxMana", this.maxMana);
            tag.putDouble("remainMana", this.remainMana);
            tag.putDouble("recoverMana", this.recoverMana);
            NbtCompound compound = new NbtCompound();
            this.holdItem.writeNbt(compound);
            tag.put("holdItem", compound);
        }

        @Override
        public void decode(NbtCompound tag) {
            this.enabled = tag.getBoolean("enabled");
            this.maxMana = tag.getDouble("maxMana");
            this.remainMana = tag.getDouble("remainMana");
            this.recoverMana = tag.getDouble("recoverMana");
            this.setHoldItem(ItemStack.fromNbt(tag.getCompound("holdItem")));
        }

        @Override
        public void tick() {
            if (this.enabled && !this.parent.player.getEntityWorld().isClient) {
                this.activePower.tick(this, this.parent.player, this.parent.player.getEntityWorld());
                this.remainMana -= this.activePower.mana();
            }
            this.remainMana += this.recoverMana;
            if (this.remainMana > this.maxMana) this.remainMana = this.maxMana;
            if (this.remainMana < 0) {
                this.remainMana += this.maxMana;
                this.parent.player.addExhaustion(4);
            }
        }

        public void keyPress() {
            if (this.activePower.isEmpty()) {
                this.disable();
                return;
            }
            if (this.isEnabled()) {
                if (this.activePower.persist()) this.disable();
                else this.activePower.unapply(this.parent.player, this.parent.player.getEntityWorld());
            } else {
                if (this.activePower.persist()) this.enable();
                else this.activePower.apply(this.parent.player, this.parent.player.getEntityWorld());
            }
            this.parent.markDirty();
        }

        public PowerType getType() {
            return this.type;
        }

        public boolean isEnabled() {
            return this.enabled;//&& this.parent.enabled;
        }

        public void setEnabled(boolean enabled) {
            if (!this.enabled && enabled)
                this.activePower.apply(parent.player, parent.player.getEntityWorld());
            if (this.enabled && !enabled)
                this.activePower.unapply(parent.player, parent.player.getEntityWorld());
            this.enabled = enabled;
            this.parent.markDirty();
        }

        public void enable() {
            this.setEnabled(true);
        }

        public void disable() {
            this.setEnabled(false);
        }

        public SongPower getActivePower() {
            return this.activePower;
        }

        private void setActivePower(SongPower activePower) {
            if (!this.activePower.isEmpty() && this.activePower != activePower)
                this.disable();
            this.activePower = activePower;
            this.parent.markDirty();
        }

        public ItemStack getHoldItem() {
            return this.holdItem;
        }

        public ItemStack pickHoldItem() {
            ItemStack holdItem = this.holdItem;
            this.holdItem = ItemStack.EMPTY;
            this.setActivePower(SongPower.EMPTY);
            return holdItem;
        }

        public void setHoldItem(ItemStack holdItem) {
            if (holdItem.getItem() instanceof SongCubeItem songCube) {
                this.holdItem = holdItem;
                this.setActivePower(songCube.getPower(holdItem));
            } else if (holdItem.isEmpty()) {
                this.holdItem = ItemStack.EMPTY;
                this.setActivePower(SongPower.EMPTY);
            } else
                throw new IllegalArgumentException("holdItem should be a song cube.");
            this.parent.markDirty();
        }

        public double getMaxMana() {
            return this.maxMana;
        }

        public double getRemainMana() {
            return this.remainMana;
        }

        public boolean hasPower() {
            return !this.holdItem.isEmpty() && !this.activePower.isEmpty();
        }
    }
}
