package com.iafenvoy.sop.screen;

import com.iafenvoy.sop.power.PowerCategory;
import com.iafenvoy.sop.power.SongPowerData;
import com.iafenvoy.sop.registry.SopKeybindings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PowerHudRenderer {
    private static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/widgets.png");
    private static final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

    public static void render(MinecraftClient client, DrawContext context) {
        assert client.player != null;
        if (client.player.isSpectator()) return;
        SongPowerData data = SongPowerData.byPlayer(client.player);
        if (!data.isEnabled()) return;
        //Render Power Slot
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        int x = width / 2 + 125, y = height - 22;
        context.drawTexture(WIDGETS_TEXTURE, x, y, 0, 0, 40, 22);
        context.drawTexture(WIDGETS_TEXTURE, x + 40, y, 140, 0, 42, 22);
        for (PowerCategory type : PowerCategory.values())
            renderOne(x, y, context, data.get(type));
    }

    private static void renderOne(int baseX, int y, DrawContext context, SongPowerData.SinglePowerData data) {
        int x = baseX + data.getType().ordinal() * 20;
        SopKeybindings.KeyBindingHolder binding = SopKeybindings.KEY_BINDINGS.get(data.getType().ordinal());
        //Render Cooldown
        String text = switch (data.getState()) {
            case ALLOW -> "§aR";
            case RECOVER -> String.format("§e%.1fs", 1.0 * data.getSecondaryCooldown() / 20);
            case DENY -> String.format("§c%.1fs", 1.0 * data.getPrimaryCooldown() / 20);
        };
        context.drawTextWithShadow(textRenderer, text, x + 2, y - 10, 0xFFFFFFFF);
        //Render Power Icon
        if (binding.isPressed() || data.isEnabled()) context.drawTexture(WIDGETS_TEXTURE, x, y, 1, 23, 23, 23);
        context.drawTexture(data.getActivePower().getIconTexture(), x + 3, y + 3, 0, 0, 16, 16, 16, 16);
    }
}
