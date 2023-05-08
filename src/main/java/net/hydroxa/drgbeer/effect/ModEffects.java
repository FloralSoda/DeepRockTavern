package net.hydroxa.drgbeer.effect;

import net.hydroxa.drgbeer.DRGBeerMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {
    public static StatusEffect FREEZE;
    public static StatusEffect NO_RAIN;
    public static StatusEffect WORMHOLE;
    public static StatusEffect TIPSY;
    public static StatusEffect BLACKOUT;
    public static StatusEffect SKOOMA;
    public static StatusEffect WATER;
    public static StatusEffect FARTY;
    public static StatusEffect FORTUNE;
    public static StatusEffect FEATHER_FALL;
    public static StatusEffect EXPLOSIVE;


    public static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(DRGBeerMod.MOD_ID, name),
                effect);
    }

    public static void registerEffects() {
        FREEZE = registerStatusEffect("freeze",
                new FreezeEffect(StatusEffectCategory.HARMFUL, 0x66FFFF));
        NO_RAIN = registerStatusEffect("no_rain",
                new NoRainEffect(StatusEffectCategory.NEUTRAL, 0xFFFF00));
        WORMHOLE = registerStatusEffect("wormhole",
                new WormholeEffect(StatusEffectCategory.NEUTRAL, 0x000099));
        TIPSY = registerStatusEffect("tipsy",
                new TipsyEffect(StatusEffectCategory.HARMFUL, 0x443100));
        BLACKOUT = registerStatusEffect("blackout",
                new BlackoutEffect(StatusEffectCategory.HARMFUL, 0x000000));
        SKOOMA = registerStatusEffect("skooma",
                new SkoomaEffect(StatusEffectCategory.BENEFICIAL, 0xFF00FF));
        WATER = registerStatusEffect("water",
                new WaterEffect(StatusEffectCategory.NEUTRAL, 0x8888FF));
        FARTY = registerStatusEffect("farty",
                new FartyEffect(StatusEffectCategory.NEUTRAL, 0x337700));
        FORTUNE = registerStatusEffect("fortune",
                new FortuneEffect(StatusEffectCategory.BENEFICIAL, 0x5500AA));
        FEATHER_FALL = registerStatusEffect("feather_fall",
                new FeatherFallEffect(StatusEffectCategory.BENEFICIAL, 0xFFFFCC));
        EXPLOSIVE = registerStatusEffect("explosive",
                new ExplosiveEffect(StatusEffectCategory.HARMFUL, 0xFFFFCC));
    }
}
