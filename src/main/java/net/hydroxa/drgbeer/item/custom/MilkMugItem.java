package net.hydroxa.drgbeer.item.custom;

import net.hydroxa.drgbeer.block.ModBlocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MilkMugItem extends MugItem{
    public MilkMugItem(Settings settings) {
        super(settings, ModBlocks.MILK_MUG.asBlock(), null, 0, 0, 0);
    }

    @Override
    public void drinkLiquid(ItemStack stack, World world, LivingEntity user) {
        super.drinkLiquid(stack, world, user);
        user.clearStatusEffects();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.isSneaking())
            return ItemUsage.consumeHeldItem(world, user, hand);
        else
            return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
