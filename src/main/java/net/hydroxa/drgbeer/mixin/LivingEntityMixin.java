package net.hydroxa.drgbeer.mixin;

import net.hydroxa.drgbeer.effect.FeatherFallEffect;
import net.hydroxa.drgbeer.effect.LowGravityEffect;
import net.hydroxa.drgbeer.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);
    @Shadow
    public abstract boolean isFallFlying();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z", at = @At(value = "HEAD", target = "Lnet/minecraft/entity/LivingEntity;handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z"), argsOnly = true, ordinal = 0)
    private float computeFallDamage(float fallDistance) {
        if (hasStatusEffect(ModEffects.FEATHER_FALL)) {
            fallDistance *= FeatherFallEffect.DAMAGE_MULTIPLIER;
        }
        if (hasStatusEffect(ModEffects.LOW_GRAVITY)) {
            fallDistance *= MINECRAFT_GRAVITY;
        }

        return fallDistance;
    }

    @Unique
    private static final float MINECRAFT_GRAVITY = 0.08f;
    @Inject(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At(value = "TAIL", target = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/Vec3d;)V"))
    public void applyGravity(CallbackInfo ci) {
        if (!hasNoGravity() && !isSubmergedInWater() && !isInLava() && !isFallFlying() && !hasStatusEffect(StatusEffects.SLOW_FALLING) && hasStatusEffect(ModEffects.LOW_GRAVITY)) {
            double newGravity = MINECRAFT_GRAVITY / LowGravityEffect.GRAVITY;
            setVelocity(getVelocity().add(0, newGravity, 0));
        }
    }
}
