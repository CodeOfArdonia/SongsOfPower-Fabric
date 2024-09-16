package com.iafenvoy.sop.registry;

import com.iafenvoy.neptune.network.PacketBufferUtils;
import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.Static;
import com.iafenvoy.sop.power.PowerType;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public final class SopKeybindings {
    public static final String CATEGORY = "key.category." + SongsOfPower.MOD_ID;
    public static final KeyBinding SELECT_POWER = new KeyBinding("key." + SongsOfPower.MOD_ID + ".select_power", GLFW.GLFW_KEY_G, CATEGORY);
    public static final KeyBinding AGGRESSIUM_POWER = new KeyBinding("key." + SongsOfPower.MOD_ID + ".aggressium_power", GLFW.GLFW_KEY_C, CATEGORY);
    public static final KeyBinding MOBILIUM_POWER = new KeyBinding("key." + SongsOfPower.MOD_ID + ".mobilium_power", GLFW.GLFW_KEY_V, CATEGORY);
    public static final KeyBinding PROTISIUM_POWER = new KeyBinding("key." + SongsOfPower.MOD_ID + ".protisium_power", GLFW.GLFW_KEY_B, CATEGORY);
    public static final KeyBinding SUPPORTIUM_POWER = new KeyBinding("key." + SongsOfPower.MOD_ID + ".supportium_power", GLFW.GLFW_KEY_N, CATEGORY);
    public static final List<KeyBindingHolder> KEY_BINDINGS;

    public static void init() {
        KeyBindingRegistryImpl.addCategory(CATEGORY);
        KeyBindingRegistryImpl.registerKeyBinding(SELECT_POWER);
        KeyBindingRegistryImpl.registerKeyBinding(AGGRESSIUM_POWER);
        KeyBindingRegistryImpl.registerKeyBinding(MOBILIUM_POWER);
        KeyBindingRegistryImpl.registerKeyBinding(PROTISIUM_POWER);
        KeyBindingRegistryImpl.registerKeyBinding(SUPPORTIUM_POWER);
        ClientTickEvents.END_CLIENT_TICK.register(client -> KEY_BINDINGS.forEach(KeyBindingHolder::tick));
        for (PowerType type : PowerType.values())
            KEY_BINDINGS.get(type.ordinal()).registerPressCallback(press -> {
                if (press) {
                    PacketByteBuf buf = PacketBufferUtils.create();
                    buf.writeEnumConstant(type);
                    ClientPlayNetworking.send(Static.KEYBINDING_SYNC, buf);
                }
            });
    }

    static {
        KEY_BINDINGS = Stream.of(AGGRESSIUM_POWER, MOBILIUM_POWER, PROTISIUM_POWER, SUPPORTIUM_POWER).map(KeyBindingHolder::new).toList();
    }

    public static class KeyBindingHolder {
        public final KeyBinding keyBinding;
        private final List<BooleanConsumer> callback = new ArrayList<>();
        private boolean pressed;

        public KeyBindingHolder(KeyBinding keyBinding) {
            this.keyBinding = keyBinding;
        }

        public void registerPressCallback(BooleanConsumer consumer) {
            this.callback.add(consumer);
        }

        public void tick() {
            boolean curr = this.keyBinding.isPressed();
            if (!this.pressed && curr) this.callback.forEach(x -> x.accept(true));
            if (this.pressed && !curr) this.callback.forEach(x -> x.accept(false));
            this.pressed = curr;
        }

        public boolean isPressed() {
            return this.pressed;
        }
    }
}
