package com.iafenvoy.sop.item;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ProtepointShieldItem extends FabricShieldItem {
    public ProtepointShieldItem() {
        super(new Settings().maxDamage(10000), 5 * 20, 0);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!stack.isOf(this)) return;
        if (entity instanceof PlayerEntity player && player.getOffHandStack() == stack) return;
        stack.setDamage(this.getMaxDamage() + 1);
        stack.setCount(0);
    }
}
