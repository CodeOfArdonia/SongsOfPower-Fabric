package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class SopParticles {
    public static final DefaultParticleType SONG_EFFECT = register("song_effect", FabricParticleTypes.simple());

    private static DefaultParticleType register(String id, DefaultParticleType type) {
        return Registry.register(Registries.PARTICLE_TYPE, new Identifier(SongsOfPower.MOD_ID, id), type);
    }

    public static void init() {
    }
}
