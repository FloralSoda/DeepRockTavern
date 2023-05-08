package net.hydroxa.drgbeer.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class EnflameEffect extends StatusEffect {
    protected EnflameEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public void onApplied(LivingEntity user, AttributeContainer attributes, int amplifier) {
        super.onApplied(user,attributes,amplifier);
        user.setOnFireFor(user.getStatusEffect(this).getDuration() / 20);
    }
}