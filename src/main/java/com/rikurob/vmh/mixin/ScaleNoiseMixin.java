package com.rikurob.vmh.mixin;

import com.rikurob.vmh.config.VmhConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LivingEntity.class)
public class ScaleNoiseMixin {

    //Scale Noise by Scale
    @Inject(method="getSoundVolume", at = @At("RETURN"), cancellable = true)
    public void injectedSoundVolume(CallbackInfoReturnable<Float> cir) {
        if (VmhConfig.doScaleNoise)
            cir.setReturnValue((cir.getReturnValue() * (float) ((LivingEntity) (Object) this).getPersistentData().getDouble("vmhScale")));
    }
}
