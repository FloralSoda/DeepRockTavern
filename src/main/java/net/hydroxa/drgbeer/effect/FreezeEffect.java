package net.hydroxa.drgbeer.effect;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FreezeEffect extends StatusEffect {
    public FreezeEffect(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        entity.setInPowderSnow(true);
        if (entity.world.getBlockState(entity.getBlockPos()).isOf(Blocks.WATER)) {
            entity.world.setBlockState(entity.getBlockPos(), Blocks.ICE.getDefaultState());
        }
        if (entity.world.getBlockState(entity.getBlockPos().up()).isOf(Blocks.WATER)) {
            entity.world.setBlockState(entity.getBlockPos().up(), Blocks.ICE.getDefaultState());
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        entity.setInPowderSnow(false);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
