package net.hydroxa.drgbeer.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class SkoomaEffect extends StatusEffect {
    public static final float Multiplier = 0.05f;

    protected SkoomaEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        float vel = amplifier+1;
        entity.addVelocity(entity.world.random.nextFloat(-Multiplier,Multiplier) * vel * 0.2, entity.world.random.nextFloat(0,Multiplier) * vel, entity.world.random.nextFloat(-Multiplier,Multiplier) * vel * 0.2);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
