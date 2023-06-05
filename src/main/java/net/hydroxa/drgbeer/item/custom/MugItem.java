package net.hydroxa.drgbeer.item.custom;

import net.hydroxa.drgbeer.effect.ModEffects;
import net.hydroxa.drgbeer.effect.TipsyEffect;
import net.hydroxa.drgbeer.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class MugItem extends BlockItem {
    private static final int MAX_USE_TIME = 100;
    public @Nullable StatusEffect effect;
    public int Duration;
    public int Amplifier;
    public int Tipsiness;

    public MugItem(Settings settings, Block block, @Nullable StatusEffect effect, int potionDuration, int potionAmplifier, int tipsiness) {
        super(block, settings);
        this.effect = effect;
        Duration = potionDuration;
        Amplifier = potionAmplifier;
        Tipsiness = tipsiness;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.isSneaking() && effect != null)
            return ItemUsage.consumeHeldItem(world, user, hand);
        else
            return TypedActionResult.fail(user.getStackInHand(hand));
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        if (context.getPlayer() == null || context.getPlayer().isSneaking() || effect == null)
            return super.place(context);
        else
            return ActionResult.PASS;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return MAX_USE_TIME;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public void blackoutEntity(LivingEntity user) {
        user.removeStatusEffect(ModEffects.TIPSY);

        user.addStatusEffect(new StatusEffectInstance(ModEffects.BLACKOUT, 600, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 600, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 600, 0));
    }

    public void incrementTipsiness(LivingEntity user) {
        if (Tipsiness > 0) {
            var tipsy = user.getStatusEffect(ModEffects.TIPSY);
            if (tipsy != null) {
                int amp = tipsy.getAmplifier();
                if (amp + Tipsiness >= 100) {
                    blackoutEntity(user);
                } else {
                    if (Tipsiness >= 100) {
                        blackoutEntity(user);
                    } else {
                        user.removeStatusEffect(ModEffects.TIPSY);

                        int newAmp = tipsy.getAmplifier() + Tipsiness;
                        user.addStatusEffect(new StatusEffectInstance(ModEffects.TIPSY, TipsyEffect.TIME_PER_LEVEL * newAmp, newAmp));
                    }
                }
            } else {
                if (Tipsiness >= 100) {
                    blackoutEntity(user);
                } else {
                    user.addStatusEffect(new StatusEffectInstance(ModEffects.TIPSY, TipsyEffect.TIME_PER_LEVEL * Tipsiness, Tipsiness));
                }
            }
        }
    }

    public void drinkLiquid(ItemStack stack, World world, LivingEntity user) {
        incrementTipsiness(user);
        if (effect != null) {
            user.addStatusEffect(new StatusEffectInstance(effect, Duration, Amplifier));
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity player = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (player instanceof ServerPlayerEntity sPlayer)
            Criteria.CONSUME_ITEM.trigger(sPlayer, stack);

        if (!world.isClient) {
            drinkLiquid(stack, world, user);
        }
        if (player != null) {
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!player.getAbilities().creativeMode)
                stack.decrement(1);
        }
        if (player == null || !player.getAbilities().creativeMode) {
            if (stack.isEmpty())
                return new ItemStack(ModItems.MUG.Item);
            if (player != null)
                player.getInventory().insertStack(new ItemStack(ModItems.MUG.Item));
        }
        world.emitGameEvent(user, GameEvent.DRINKING_FINISH, user.getCameraBlockPos());
        return stack;
    }
}
