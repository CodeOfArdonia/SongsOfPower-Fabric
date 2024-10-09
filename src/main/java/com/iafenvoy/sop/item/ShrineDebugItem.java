package com.iafenvoy.sop.item;

import com.iafenvoy.sop.world.ShrineStructureHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ShrineDebugItem extends Item {
    public ShrineDebugItem() {
        super(new Settings().rarity(Rarity.EPIC).maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!(world instanceof ServerWorld serverWorld)) return TypedActionResult.success(stack);
        if (user.isSneaking() && user.isCreative())
            ShrineStructureHelper.generate(user.getBlockPos(), serverWorld);
        else
            user.sendMessage(Text.literal(ShrineStructureHelper.match(user.getBlockPos(), serverWorld) ? "Match" : "Not Match"));
        user.getItemCooldownManager().set(this, 20 * 5);
        return super.use(world, user, hand);
    }
}
