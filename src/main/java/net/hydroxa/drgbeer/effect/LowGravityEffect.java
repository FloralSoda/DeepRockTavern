package net.hydroxa.drgbeer.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class LowGravityEffect extends StatusEffect {
    public static final float GRAVITY = 1.13f;
    protected LowGravityEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
}
