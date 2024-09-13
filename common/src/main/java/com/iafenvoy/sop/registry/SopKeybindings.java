package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

import java.util.List;

@Environment(EnvType.CLIENT)
public final class SopKeybindings {
    public static final String CATEGORY = "key.category." + SongsOfPower.MOD_ID;
    public static final KeyBinding SELECT_POWER = new KeyBinding("key." + SongsOfPower.MOD_ID + ".select_power", GLFW.GLFW_KEY_G, CATEGORY);
    public static final KeyBinding AGGRESSIUM_POWER = new KeyBinding("key." + SongsOfPower.MOD_ID + ".aggressium_power", GLFW.GLFW_KEY_C, CATEGORY);
    public static final KeyBinding MOBILIUM_POWER = new KeyBinding("key." + SongsOfPower.MOD_ID + ".mobilium_power", GLFW.GLFW_KEY_V, CATEGORY);
    public static final KeyBinding PROTISIUM_POWER = new KeyBinding("key." + SongsOfPower.MOD_ID + ".protisium_power", GLFW.GLFW_KEY_B, CATEGORY);
    public static final KeyBinding SUPPORTIUM_POWER = new KeyBinding("key." + SongsOfPower.MOD_ID + ".supportium_power", GLFW.GLFW_KEY_N, CATEGORY);
    public static final List<KeyBinding> KEY_BINDINGS;

    public static void init() {
        KeyMappingRegistry.register(SELECT_POWER);
        KeyMappingRegistry.register(AGGRESSIUM_POWER);
        KeyMappingRegistry.register(MOBILIUM_POWER);
        KeyMappingRegistry.register(PROTISIUM_POWER);
        KeyMappingRegistry.register(SUPPORTIUM_POWER);
    }

    static {
        KEY_BINDINGS = List.of(AGGRESSIUM_POWER, MOBILIUM_POWER, PROTISIUM_POWER, SUPPORTIUM_POWER);
    }
}
