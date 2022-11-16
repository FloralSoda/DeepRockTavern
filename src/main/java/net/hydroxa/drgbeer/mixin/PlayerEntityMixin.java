package net.hydroxa.drgbeer.mixin;

import net.hydroxa.drgbeer.effect.ModEffects;
import net.hydroxa.drgbeer.sound.ModSounds;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "isSwimming()Z", at = @At("RETURN"), cancellable = true)
    public void forceSwim(CallbackInfoReturnable<Boolean> callback) {
        if ((Object)this instanceof ClientPlayerEntity cpe) {
            if (cpe.hasStatusEffect(ModEffects.BLACKOUT)) {
                callback.setReturnValue(true);
            } else if (cpe.hasStatusEffect(ModEffects.FREEZE)) {
                callback.setReturnValue(false);
            }
        }
    }
}
