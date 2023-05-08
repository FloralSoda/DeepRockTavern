package net.hydroxa.drgbeer.mixin;

import net.hydroxa.drgbeer.effect.ModEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isSwimming()Z", at = @At("RETURN"), cancellable = true)
    public void forceSwim(CallbackInfoReturnable<Boolean> callback) {
        if (hasStatusEffect(ModEffects.BLACKOUT)) {
            callback.setReturnValue(true);
        } else if (hasStatusEffect(ModEffects.FREEZE)) {
            callback.setReturnValue(false);
        }
    }
}
