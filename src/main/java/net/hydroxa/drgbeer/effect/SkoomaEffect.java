package net.hydroxa.drgbeer.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class SkoomaEffect extends StatusEffect {
    public static final float Multiplier = 0.01f;

    protected SkoomaEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        float vel = amplifier+1;
        entity.addVelocity(entity.world.random.nextFloat(-Multiplier,Multiplier) * vel, entity.world.random.nextFloat(0,Multiplier) * vel, entity.world.random.nextFloat(-Multiplier,Multiplier) * vel);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
