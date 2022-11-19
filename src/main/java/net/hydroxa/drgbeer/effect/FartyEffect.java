package net.hydroxa.drgbeer.effect;

import net.hydroxa.drgbeer.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FartyEffect extends StatusEffect {
    public FartyEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    public static final float thrust = 0.2f;
    public static final float crouchMultiplier = 2f;
    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.playSound(ModSounds.FART, 1, 1);
        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.world.random.nextFloat() < 0.2) {
            entity.playSound(ModSounds.FART, 1, entity.world.random.nextFloat(0.8f,2f));
            entity.addVelocity(0, thrust * amplifier, 0);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
