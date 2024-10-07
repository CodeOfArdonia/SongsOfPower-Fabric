package com.iafenvoy.sop.config;

import com.iafenvoy.jupiter.config.AutoInitConfigContainer;
import com.iafenvoy.jupiter.malilib.config.options.ConfigDouble;
import com.iafenvoy.jupiter.malilib.config.options.ConfigInteger;
import com.iafenvoy.sop.SongsOfPower;
import net.minecraft.util.Identifier;

public class SopConfig extends AutoInitConfigContainer {
    public static final SopConfig INSTANCE = new SopConfig();

    public final AggressiumPowerConfig aggressium = new AggressiumPowerConfig();
    public final MobiliumPowerConfig mobilium = new MobiliumPowerConfig();
    public final ProtisiumPowerConfig protisium = new ProtisiumPowerConfig();
    public final SupporiumPowerConfig supportium = new SupporiumPowerConfig();

    public SopConfig() {
        super(new Identifier(SongsOfPower.MOD_ID, "sop_config"), "config.sop.config.server.title", "./config/sow/songs-of-power.json");
    }

    @Override
    protected boolean shouldCompressKey() {
        return false;
    }

    public static class AggressiumPowerConfig extends AutoInitConfigCategoryBase {
        public final ConfigInteger aggrospherePrimaryCooldown = new ConfigInteger("config.sop.power.aggrosphere.cooldown.primary", 10, 0, Integer.MAX_VALUE);
        public final ConfigInteger aggrosphereSecondaryCooldown = new ConfigInteger("config.sop.power.aggrosphere.cooldown.secondary", 10, 0, Integer.MAX_VALUE);
        public final ConfigDouble aggrosphereExhaustion = new ConfigDouble("config.sop.power.aggrosphere.exhaustion", 1, 0, Integer.MAX_VALUE);
        public final ConfigDouble aggrosphereSpeed = new ConfigDouble("config.sop.power.aggrosphere.speed", 3, 0, Integer.MAX_VALUE);
        public final ConfigDouble aggrosphereDamage = new ConfigDouble("config.sop.power.aggrosphere.damage", 5, 0, Integer.MAX_VALUE);
        public final ConfigInteger aggroquakePrimaryCooldown = new ConfigInteger("config.sop.power.aggroquake.cooldown.primary", 300, 0, Integer.MAX_VALUE);
        public final ConfigInteger aggroquakeSecondaryCooldown = new ConfigInteger("config.sop.power.aggroquake.cooldown.secondary", 200, 0, Integer.MAX_VALUE);
        public final ConfigDouble aggroquakeExhaustion = new ConfigDouble("config.sop.power.aggroquake.exhaustion", 4, 0, Integer.MAX_VALUE);
        public final ConfigDouble aggroquakeRange = new ConfigDouble("config.sop.power.aggroquake.range", 8, 0, Integer.MAX_VALUE);
        public final ConfigDouble aggroquakeDamage = new ConfigDouble("config.sop.power.aggroquake.damage", 5, 0, Integer.MAX_VALUE);

        public AggressiumPowerConfig() {
            super("aggressium", "config.sop.category.power.aggressium");
        }
    }

    public static class MobiliumPowerConfig extends AutoInitConfigCategoryBase {
        public final ConfigInteger mobiliflashPrimaryCooldown = new ConfigInteger("config.sop.power.mobiliflash.cooldown.primary", 40, 0, Integer.MAX_VALUE);
        public final ConfigInteger mobiliflashSecondaryCooldown = new ConfigInteger("config.sop.power.mobiliflash.cooldown.secondary", 40, 0, Integer.MAX_VALUE);
        public final ConfigDouble mobiliflashExhaustion = new ConfigDouble("config.sop.power.mobiliflash.exhaustion", 2, 0, Integer.MAX_VALUE);
        public final ConfigDouble mobiliflashSpeed = new ConfigDouble("config.sop.power.mobiliflash.speed", 8, 0, 50);
        public final ConfigDouble mobiliwingsExhaustion = new ConfigDouble("config.sop.power.mobiliwings.exhaustion", 1.0 / 10, 0, Integer.MAX_VALUE);
        public final ConfigDouble mobiliglideExhaustion = new ConfigDouble("config.sop.power.mobiliglide.exhaustion", 2.0 / 10, 0, Integer.MAX_VALUE);

        public MobiliumPowerConfig() {
            super("mobilium", "config.sop.category.power.mobilium");
        }
    }

    public static class ProtisiumPowerConfig extends AutoInitConfigCategoryBase {
        public final ConfigDouble protesphereExhaustion = new ConfigDouble("config.sop.power.protesphere.exhaustion", 2.0 / 10, 0, Integer.MAX_VALUE);
        public final ConfigDouble protepointExhaustion = new ConfigDouble("config.sop.power.protepoint.exhaustion", 1.0 / 10, 0, Integer.MAX_VALUE);
        public final ConfigInteger protehealPrimaryCooldown = new ConfigInteger("config.sop.power.proteheal.cooldown.primary", 100, 0, Integer.MAX_VALUE);
        public final ConfigInteger protehealSecondaryCooldown = new ConfigInteger("config.sop.power.proteheal.cooldown.secondary", 200, 0, Integer.MAX_VALUE);
        public final ConfigDouble protehealExhaustion = new ConfigDouble("config.sop.power.proteheal.exhaustion", 2, 0, Integer.MAX_VALUE);

        public ProtisiumPowerConfig() {
            super("protisium", "config.sop.category.power.protisium");
        }
    }

    public static class SupporiumPowerConfig extends AutoInitConfigCategoryBase {
        public final ConfigInteger supporoliftPrimaryCooldown = new ConfigInteger("config.sop.power.supporolift.cooldown.primary", 100, 0, Integer.MAX_VALUE);
        public final ConfigInteger supporoliftSecondaryCooldown = new ConfigInteger("config.sop.power.supporolift.cooldown.secondary", 200, 0, Integer.MAX_VALUE);
        public final ConfigDouble supporoliftExhaustion = new ConfigDouble("config.sop.power.supporolift.exhaustion", 2, 0, Integer.MAX_VALUE);
        public final ConfigDouble supporoliftRange = new ConfigDouble("config.sop.power.supporolift.range", 20, 0, 16 * 16);

        public SupporiumPowerConfig() {
            super("supportium", "config.sop.category.power.supportium");
        }
    }
}
