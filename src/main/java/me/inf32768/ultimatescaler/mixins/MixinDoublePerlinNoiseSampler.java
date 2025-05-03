package me.inf32768.ultimatescaler.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;

import static me.inf32768.ultimatescaler.UltimateScaler.config;

@Mixin(DoublePerlinNoiseSampler.class)
public class MixinDoublePerlinNoiseSampler {
    @ModifyVariable(method = "sample", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private double modifyX(double x) {
        return x * config.globalXScale + config.globalXOffset;
    }
    @ModifyVariable(method = "sample", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private double modifyY(double y) {
        return y * config.globalYScale + config.globalYOffset;
    }
    @ModifyVariable(method = "sample", at = @At("HEAD"), ordinal = 2, argsOnly = true)
    private double modifyZ(double z) {
        return z * config.globalZScale + config.globalZOffset;
    }
}
