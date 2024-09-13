package com.iafenvoy.sop.screen;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.registry.SopKeybindings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PowerHudRenderer {
    private static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/widgets.png");
    private static final Identifier BARS_TEXTURE = new Identifier(SongsOfPower.MOD_ID, "textures/gui/bars.png");

    public static void render(MinecraftClient client, DrawContext context, float tickDelta) {
        renderOne(context, ItemStack.EMPTY, 0, 1);
        renderOne(context, ItemStack.EMPTY, 1, 3);
        renderOne(context, ItemStack.EMPTY, 2, 0);
        renderOne(context, ItemStack.EMPTY, 3, 2);
    }

    private static void renderOne(DrawContext context, ItemStack stack, int ordinal, int color) {
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        int x = width / 2 + 100 + ordinal * 30, y = height - 22;
        KeyBinding binding = SopKeybindings.KEY_BINDINGS.get(ordinal);
        context.drawTexture(BARS_TEXTURE, x - 7, y - 38, 13 + 13 * color, 0, 7, 60);
        context.drawTexture(WIDGETS_TEXTURE, x, y, 60, 23, 22, 22);
        if (binding.isPressed()) context.drawTexture(WIDGETS_TEXTURE, x, y, 1, 23, 23, 23);
        if (stack.isEmpty()) stack = new ItemStack(Items.BARRIER);
        context.drawItem(stack, x + 3, y + 3);
    }
}
