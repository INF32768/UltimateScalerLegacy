package me.inf32768.ultimatescaler.mixins;

import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static me.inf32768.ultimatescaler.UltimateScaler.config;

@Mixin(DensityFunctionTypes.Offset.class)
public interface MixinShiftSampler {
    @ModifyArgs(method = "sample", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/densityfunction/DensityFunction$Noise;sample(DDD)D"))
    private void modifyNoiseSampleArgs(Args args) {
        double x = args.get(0);
        double y = args.get(1);
        double z = args.get(2);

        x = x * config.globalXScale + config.globalXOffset * (double) 0.25F;
        y = y * config.globalYScale + config.globalYOffset * (double) 0.25F;
        z = z * config.globalZScale + config.globalZOffset * (double) 0.25F;

        args.set(0, x);
        args.set(1, y);
        args.set(2, z);
    }
}
