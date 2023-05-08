package net.hydroxa.drgbeer.mixin;

import net.hydroxa.drgbeer.effect.FeatherFallEffect;
import net.hydroxa.drgbeer.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z", at = @At(value = "HEAD", target = "Lnet/minecraft/entity/LivingEntity;handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z"), argsOnly = true, ordinal = 1)
    private float computeFallDamage(float damageMultiplier) {
        if (hasStatusEffect(ModEffects.FEATHER_FALL)) {
            return damageMultiplier * FeatherFallEffect.DAMAGE_MULTIPLIER;
        }

        return damageMultiplier;
    }
}
