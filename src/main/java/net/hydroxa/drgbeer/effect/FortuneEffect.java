package net.hydroxa.drgbeer.effect;

import net.hydroxa.drgbeer.DRGBeerMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

public class FortuneEffect extends StatusEffect {
    public static final Identifier LOOT_TABLE = new Identifier(DRGBeerMod.MOD_ID, "effects/fortune");

    protected FortuneEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
}
