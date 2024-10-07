package com.iafenvoy.sop.item;

import com.iafenvoy.sop.power.AbstractSongPower;
import com.iafenvoy.sop.power.PowerCategory;
import com.iafenvoy.sop.world.ShrineStructureHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongCubeItem extends Item {
    public static final Map<PowerCategory, Item> SONGS = new HashMap<>();
    public static final String POWER_TYPE_KEY = "power_type";
    private final PowerCategory type;

    public SongCubeItem(PowerCategory type) {
        super(new Settings().rarity(Rarity.EPIC).maxCount(1));
        SONGS.put(type, this);
        this.type = type;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        AbstractSongPower<?> power = this.getPower(stack);
        tooltip.add(Text.translatable(power.getTranslateKey()).formatted(this.type.getColor()));
        if (power.isExperimental())
            tooltip.add(Text.translatable("item.sop.song.experimental"));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld() instanceof ServerWorld serverWorld && context.getPlayer() instanceof ServerPlayerEntity player) {
            if (context.getPlayer().isSneaking())
                ShrineStructureHelper.generate(player.getBlockPos(), serverWorld);
            else
                player.sendMessage(Text.literal(ShrineStructureHelper.match(player.getBlockPos(), serverWorld) ? "Match" : "Not Match"));
        }
        return super.useOnBlock(context);
    }

    public AbstractSongPower<?> getPower(ItemStack stack) {
        return this.type.getPowerById(stack.getOrCreateNbt().getString(POWER_TYPE_KEY));
    }

    public PowerCategory getType() {
        return this.type;
    }
}
