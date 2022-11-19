package net.hydroxa.drgbeer.mixin;

import net.hydroxa.drgbeer.effect.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "isSwimming()Z", at = @At("RETURN"), cancellable = true)
    public void forceSwim(CallbackInfoReturnable<Boolean> callback) {
        PlayerEntity pe = (PlayerEntity) (Object) this;
        if (pe.hasStatusEffect(ModEffects.BLACKOUT)) {
            callback.setReturnValue(true);
        } else if (pe.hasStatusEffect(ModEffects.FREEZE)) {
            callback.setReturnValue(false);
        }
    }
}
