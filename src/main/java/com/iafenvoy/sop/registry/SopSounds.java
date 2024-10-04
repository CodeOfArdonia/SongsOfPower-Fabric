package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class SopSounds {
    public static final SoundEvent AGGROSPHERE = register("aggrosphere");
    public static final SoundEvent MOBILIFLASH = register("mobiliflash");
    public static final SoundEvent PROTEPOINT = register("protepoint");
    public static final SoundEvent PROTESPHERE = register("protesphere");
    public static final SoundEvent PROTESPHERE_UNAPPLY = register("protesphere_unapply");

    private static SoundEvent register(String id) {
        return Registry.register(Registries.SOUND_EVENT, new Identifier(SongsOfPower.MOD_ID, id), SoundEvent.of(Identifier.of(SongsOfPower.MOD_ID, id)));
    }

    public static void init() {
    }
}
