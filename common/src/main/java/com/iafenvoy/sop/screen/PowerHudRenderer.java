package com.iafenvoy.sop.screen;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.data.PowerType;
import com.iafenvoy.sop.data.SongPowerData;
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
        SongPowerData data = SongPowerData.byPlayer(client.player);
        for (PowerType type : PowerType.values())
            renderOne(context, data.get(type));
    }

    private static void renderOne(DrawContext context, SongPowerData.SinglePowerData data) {
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        int x = width / 2 + 100 + data.getType().ordinal() * 30, y = height - 22;
        KeyBinding binding = SopKeybindings.KEY_BINDINGS.get(data.getType().ordinal());
        //Render Mana Bar
        final int maxHeight = 60;
        context.drawTexture(BARS_TEXTURE, x - 7, y - 38, 13 + 13 * data.getType().getColorOffset(), 0, 7, maxHeight);
        int newHeight = (int) (maxHeight * data.getRemainMana() / data.getMaxMana());
        context.drawTexture(BARS_TEXTURE, x - 7, y - 38, 13 + 13 * data.getType().getColorOffset(), maxHeight - newHeight, 7, newHeight);
        //Render Power Icon/Slot
        context.drawTexture(WIDGETS_TEXTURE, x, y, 60, 23, 22, 22);
        if (binding.isPressed()) context.drawTexture(WIDGETS_TEXTURE, x, y, 1, 23, 23, 23);
        ItemStack stack = data.getActivePower().icon();
        if (stack.isEmpty()) stack = new ItemStack(Items.BARRIER);
        context.drawItem(stack, x + 3, y + 3);
    }
}
