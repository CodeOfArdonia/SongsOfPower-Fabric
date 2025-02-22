package com.iafenvoy.sop.power;

import com.iafenvoy.sop.component.SongPowerComponent;
import com.iafenvoy.sop.item.block.AbstractSongCubeBlock;
import com.iafenvoy.sop.util.Serializable;
import com.iafenvoy.sop.util.Tickable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;

import java.util.HashMap;
import java.util.Map;

public class SongPowerData implements Serializable, Tickable {
    private final PlayerEntity player;
    private boolean enabled = false;
    private final Map<PowerCategory, SinglePowerData> byType = new HashMap<>();
    private final SinglePowerData aggressium = new SinglePowerData(this, PowerCategory.AGGRESSIUM);
    private final SinglePowerData mobilium = new SinglePowerData(this, PowerCategory.MOBILIUM);
    private final SinglePowerData protisium = new SinglePowerData(this, PowerCategory.PROTISIUM);
    private final SinglePowerData supportium = new SinglePowerData(this, PowerCategory.SUPPORTIUM);
    private boolean dirty = false;

    public SongPowerData(PlayerEntity player) {
        this.player = player;
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
        if (this.player.getEntityWorld() instanceof ServerWorld serverWorld)
            PowerMergeHelper.run(this, this.player, serverWorld);
    }

    public void dropAll(){
        this.aggressium.drop();
        this.mobilium.drop();
        this.protisium.drop();
        this.supportium.drop();
    }

    public boolean isDirty() {
        boolean dirty = this.dirty;
        this.dirty = false;
        return dirty;
    }

    public void markDirty() {
        this.dirty = true;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void enable() {
        this.setEnabled(true);
    }

    public void disable() {
        this.setEnabled(false);
    }

    public SinglePowerData get(PowerCategory type) {
        return this.byType.get(type);
    }

    public boolean powerEnabled(PowerCategory type, AbstractSongPower<?> power) {
        SinglePowerData data = this.get(type);
        return data.hasPower() && data.isEnabled() && data.getActivePower() == power;
    }

    public static SongPowerData byPlayer(PlayerEntity player) {
        return SongPowerComponent.SONG_POWER_COMPONENT.get(player).getData();
    }

    public static class SinglePowerData implements Serializable, Tickable {
        private final SongPowerData parent;
        private final PowerCategory type;
        private AbstractSongPower<?> activePower = DummySongPower.EMPTY;
        private boolean enabled = false;
        private int primaryCooldown = 0;
        private int secondaryCooldown = 0;

        public SinglePowerData(SongPowerData parent, PowerCategory type) {
            this.parent = parent;
            this.type = type;
            this.parent.byType.put(type, this);
        }

        @Override
        public void encode(NbtCompound tag) {
            tag.putBoolean("enabled", this.enabled);
            tag.putInt("primaryCooldown", this.primaryCooldown);
            tag.putInt("secondaryCooldown", this.secondaryCooldown);
            tag.putString("activePower", this.activePower.getId());
        }

        @Override
        public void decode(NbtCompound tag) {
            this.enabled = tag.getBoolean("enabled");
            this.primaryCooldown = tag.getInt("primaryCooldown");
            this.secondaryCooldown = tag.getInt("secondaryCooldown");
            this.activePower = AbstractSongPower.BY_ID.getOrDefault(tag.getString("activePower"), DummySongPower.EMPTY);
        }

        @Override
        public void tick() {
            State state = this.getState();
            if (state == State.DENY) {
                this.primaryCooldown--;
                this.parent.markDirty();
            } else if (state == State.RECOVER) {
                this.secondaryCooldown--;
                this.parent.markDirty();
            }
            if (this.isEnabled() && !this.parent.player.getEntityWorld().isClient && this.activePower instanceof PersistSongPower persistSongPower) {
                if (persistSongPower.tick(this)) this.disable();
                this.parent.markDirty();
            }
        }

        public void keyPress() {
            if (this.activePower.isEmpty()) {
                this.disable();
                return;
            }
            if (this.getState() == State.DENY) return;
            if (this.isEnabled()) {
                if (this.activePower.isPersist()) this.disable();
                else this.activePower.unapply(this);
            } else {
                if (this.activePower.isPersist()) this.enable();
                else {
                    if (this.getState() == State.RECOVER) {
                        this.getPlayer().addExhaustion(this.activePower.getExhaustion(this));
                        this.secondaryCooldown = 0;
                    }
                    if (this.getState() == State.ALLOW) this.activePower.apply(this);
                }
            }
        }

        public PowerCategory getType() {
            return this.type;
        }

        public boolean isEnabled() {
            return this.enabled && this.parent.enabled;
        }

        public void setEnabled(boolean enabled) {
            if (!this.enabled && enabled)
                this.activePower.apply(this);
            if (this.enabled && !enabled)
                this.activePower.unapply(this);
            this.enabled = enabled;
            this.parent.markDirty();
        }

        public void enable() {
            this.setEnabled(true);
        }

        public void disable() {
            this.setEnabled(false);
        }

        public AbstractSongPower<?> getActivePower() {
            return this.activePower;
        }

        public void drop() {
            Block.dropStack(this.getPlayer().getEntityWorld(), this.getPlayer().getBlockPos(), this.activePower.getStack());
            this.setActivePower(DummySongPower.EMPTY);
        }

        public void setActivePower(AbstractSongPower<?> activePower) {
            if (!this.activePower.isEmpty() && this.activePower != activePower)
                this.disable();
            this.activePower = activePower;
            this.parent.markDirty();
        }

        public void setHoldItem(ItemStack holdItem) {
            if (holdItem.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractSongCubeBlock songCube)
                this.setActivePower(songCube.getPower(holdItem));
            else if (holdItem.isEmpty())
                this.setActivePower(DummySongPower.EMPTY);
            else
                throw new IllegalArgumentException("holdItem should be a song cube.");
        }

        public boolean hasPower() {
            return !this.activePower.isEmpty();
        }

        public PlayerEntity getPlayer() {
            return this.parent.player;
        }

        public int getPrimaryCooldown() {
            return this.primaryCooldown;
        }

        public int getSecondaryCooldown() {
            return this.secondaryCooldown;
        }

        public State getState() {
            if (this.primaryCooldown > 0) return State.DENY;
            if (this.secondaryCooldown > 0) return State.RECOVER;
            return State.ALLOW;
        }

        public void cooldown() {
            this.primaryCooldown = this.activePower.getPrimaryCooldown(this);
            this.secondaryCooldown = this.activePower.getSecondaryCooldown(this);
        }
    }

    public enum State {
        ALLOW, RECOVER, DENY;
    }
}
