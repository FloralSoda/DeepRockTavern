package net.hydroxa.drgbeer.effect;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class FreezeEffect extends StatusEffect {
    public FreezeEffect(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        entity.setInPowderSnow(true);
        entity.world.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_PLAYER_HURT_FREEZE, SoundCategory.PLAYERS, 1f, 1.5f, true);
        if (entity.world.getBlockState(entity.getBlockPos()).isOf(Blocks.WATER)) {
            entity.world.setBlockState(entity.getBlockPos(), Blocks.ICE.getDefaultState());
        }
        if (entity.world.getBlockState(entity.getBlockPos().up()).isOf(Blocks.WATER)) {
            entity.world.setBlockState(entity.getBlockPos().up(), Blocks.ICE.getDefaultState());
        }

        if (entity.world.isClient) {
            MinecraftClient.getInstance().options.setPerspective(Perspective.THIRD_PERSON_BACK);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        entity.setInPowderSnow(false);

        if (entity.world.isClient) {
            MinecraftClient.getInstance().options.setPerspective(Perspective.FIRST_PERSON);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
