package com.iafenvoy.sop.item;

import com.iafenvoy.sop.power.PowerType;
import com.iafenvoy.sop.power.SongPower;
import com.iafenvoy.sop.registry.SopItemGroups;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongCubeItem extends Item {
    public static final Map<PowerType, Item> SONGS = new HashMap<>();
    public static final String POWER_TYPE_KEY = "power_type";
    private final PowerType type;

    public SongCubeItem(PowerType type) {
        super(new Settings().rarity(Rarity.EPIC).maxCount(1).arch$tab(SopItemGroups.MAIN));
        SONGS.put(type, this);
        this.type = type;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(this.getPower(stack).getTranslateKey()).formatted(this.type.getColor()));
    }

    public SongPower getPower(ItemStack stack) {
        return this.type.getPowerById(stack.getOrCreateNbt().getString(POWER_TYPE_KEY));
    }

    public PowerType getType() {
        return this.type;
    }
}
