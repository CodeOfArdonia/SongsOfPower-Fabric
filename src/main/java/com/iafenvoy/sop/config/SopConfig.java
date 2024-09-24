package com.iafenvoy.sop.config;

import com.iafenvoy.jupiter.config.AutoInitConfigContainer;
import com.iafenvoy.jupiter.malilib.config.options.ConfigDouble;
import com.iafenvoy.jupiter.malilib.config.options.ConfigInteger;
import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.power.PowerCategory;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SopConfig extends AutoInitConfigContainer {
    public static final SopConfig INSTANCE = new SopConfig();

    public final ManaConfig mana = new ManaConfig();
    public final PowerConfig power = new PowerConfig();

    public SopConfig() {
        super(new Identifier(SongsOfPower.MOD_ID, "sop_config"), "config.sop.config.server.title", "./config/sow/songs-of-power.json");
    }

    public static class ManaConfig extends AutoInitConfigCategoryBase {
        public static final Map<PowerCategory, ConfigDouble> MAX_MANA = new HashMap<>();
        public static final Map<PowerCategory, ConfigDouble> RECOVER_MANA = new HashMap<>();

        public final ConfigDouble aggressiumMaxMana = new ConfigDouble("config.sop.mana.aggressium.max", 100, 0, Double.MAX_VALUE);
        public final ConfigDouble aggressiumRecoverMana = new ConfigDouble("config.sop.mana.aggressium.recover", 0.5, 0, Double.MAX_VALUE);
        public final ConfigDouble mobiliumMaxMana = new ConfigDouble("config.sop.mana.mobilium.max", 100, 0, Double.MAX_VALUE);
        public final ConfigDouble mobiliumRecoverMana = new ConfigDouble("config.sop.mana.mobilium.recover", 0.5, 0, Double.MAX_VALUE);
        public final ConfigDouble protisiumMaxMana = new ConfigDouble("config.sop.mana.protisium.max", 100, 0, Double.MAX_VALUE);
        public final ConfigDouble protisiumRecoverMana = new ConfigDouble("config.sop.mana.protisium.recover", 0.5, 0, Double.MAX_VALUE);
        public final ConfigDouble supportiumMaxMana = new ConfigDouble("config.sop.mana.supportium.max", 100, 0, Double.MAX_VALUE);
        public final ConfigDouble supportiumRecoverMana = new ConfigDouble("config.sop.mana.supportium.recover", 0.5, 0, Double.MAX_VALUE);

        public ManaConfig() {
            super("mana", "config.sop.category.mana");
            MAX_MANA.put(PowerCategory.AGGRESSIUM, this.aggressiumMaxMana);
            MAX_MANA.put(PowerCategory.MOBILIUM, this.mobiliumMaxMana);
            MAX_MANA.put(PowerCategory.PROTISIUM, this.protisiumMaxMana);
            MAX_MANA.put(PowerCategory.SUPPORTIUM, this.supportiumMaxMana);
            RECOVER_MANA.put(PowerCategory.AGGRESSIUM, this.aggressiumRecoverMana);
            RECOVER_MANA.put(PowerCategory.MOBILIUM, this.mobiliumRecoverMana);
            RECOVER_MANA.put(PowerCategory.PROTISIUM, this.protisiumRecoverMana);
            RECOVER_MANA.put(PowerCategory.SUPPORTIUM, this.supportiumRecoverMana);
        }
    }

    public static class PowerConfig extends AutoInitConfigCategoryBase {
        public final ConfigDouble aggrosphereMana = new ConfigDouble("config.sop.power.aggrosphere.mana", 10, 0, Integer.MAX_VALUE);
        public final ConfigInteger aggrosphereCooldown = new ConfigInteger("config.sop.power.aggrosphere.cooldown", 10, 0, Integer.MAX_VALUE);
        public final ConfigDouble mobiliflashMana = new ConfigDouble("config.sop.power.mobiliflash.mana", 30, 0, Integer.MAX_VALUE);
        public final ConfigInteger mobiliflashCooldown = new ConfigInteger("config.sop.power.mobiliflash.cooldown", 40, 0, Integer.MAX_VALUE);
        public final ConfigDouble mobiliwingsMana = new ConfigDouble("config.sop.power.mobiliwings.mana", 1, 0, Integer.MAX_VALUE);
        public final ConfigDouble mobiliglideMana = new ConfigDouble("config.sop.power.mobiliglide.mana", 1, 0, Integer.MAX_VALUE);
        public final ConfigDouble protesphereMana = new ConfigDouble("config.sop.power.protesphere.mana", 2, 0, Integer.MAX_VALUE);
        public final ConfigDouble protepointMana = new ConfigDouble("config.sop.power.protepoint.mana", 1, 0, Integer.MAX_VALUE);
        public final ConfigDouble protehealMana = new ConfigDouble("config.sop.power.proteheal.mana", 2, 0, Integer.MAX_VALUE);
        public final ConfigInteger protehealCooldown = new ConfigInteger("config.sop.power.proteheal.cooldown", 200, 0, Integer.MAX_VALUE);
        public final ConfigDouble supporoliftMana = new ConfigDouble("config.sop.power.supporolift.mana", 50, 0, Integer.MAX_VALUE);
        public final ConfigInteger supporoliftCooldown = new ConfigInteger("config.sop.power.supporolift.cooldown", 200, 0, Integer.MAX_VALUE);

        public PowerConfig() {
            super("power", "config.sop.category.power");
        }
    }
}
