package net.hydroxa.drgbeer.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.hydroxa.drgbeer.effect.ModEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(value= EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin{
    @Inject(method = "isSneaking()Z",at = @At("RETURN"), cancellable = true)
    public void forceSneak(CallbackInfoReturnable<Boolean> callback) {
        ClientPlayerEntity cpe = (ClientPlayerEntity)(Object)this;
        if (cpe.hasStatusEffect(ModEffects.FREEZE) || cpe.hasStatusEffect(ModEffects.BLACKOUT))
            callback.setReturnValue(false);
        else if (cpe.hasStatusEffect(ModEffects.NO_RAIN))
            callback.setReturnValue(true);
    }

    @Inject(method = "canMoveVoluntarily()Z",at=@At("RETURN"),cancellable = true)
    public void freezeMovement(CallbackInfoReturnable<Boolean> callback) {
        ClientPlayerEntity cpe = (ClientPlayerEntity)(Object)this;
        if (cpe.hasStatusEffect(ModEffects.FREEZE) || cpe.hasStatusEffect(ModEffects.BLACKOUT))
            callback.setReturnValue(false);
    }
}
