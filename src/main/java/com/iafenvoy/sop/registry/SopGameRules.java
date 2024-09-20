package com.iafenvoy.sop.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public final class SopGameRules {
    public static final GameRules.Key<GameRules.BooleanRule> SHOW_PARTICLE = GameRuleRegistry.register("sop:show_particle", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));

    public static void init() {
    }
}
