package me.inf32768.ultimatescaler.mixins;

import com.mojang.serialization.Codec;
import me.inf32768.ultimatescaler.UltimateScaler;
import me.inf32768.ultimatescaler.config.WorldGenOptions;
import net.minecraft.util.math.noise.InterpolatedNoiseSampler;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.inf32768.ultimatescaler.UltimateScaler.config;

@Mixin(InterpolatedNoiseSampler.class)
public abstract class MixinInterpolatedNoiseSampler {
    @Mutable
    @Shadow
    @Final
    private static Codec<Double> SCALE_AND_FACTOR_RANGE;

    @Mutable
    @Shadow
    @Final
    private double xzScale;

    @Mutable
    @Shadow
    @Final
    private double yScale;

    @ModifyConstant(
            constant = @Constant(
                    doubleValue = 684.412D,
                    ordinal = 0
            ),
            method = "<init>(Lnet/minecraft/util/math/noise/OctavePerlinNoiseSampler;Lnet/minecraft/util/math/noise/OctavePerlinNoiseSampler;Lnet/minecraft/util/math/noise/OctavePerlinNoiseSampler;DDDDD)V"
    )
    private static double setXZCoordinateScale(double original) {
        return UltimateScaler.config == null ? original : UltimateScaler.config.xzCoordinateScale;
    }

    @ModifyConstant(
            constant = @Constant(
                    doubleValue = 684.412D,
                    ordinal = 1
            ),
            method = "<init>(Lnet/minecraft/util/math/noise/OctavePerlinNoiseSampler;Lnet/minecraft/util/math/noise/OctavePerlinNoiseSampler;Lnet/minecraft/util/math/noise/OctavePerlinNoiseSampler;DDDDD)V"
    )
    private static double setYCoordinateScale(double original) {
        return UltimateScaler.config == null ? original : UltimateScaler.config.yCoordinateScale;
    }

    @Inject(at = @At(value = "RETURN"), method = "<init>(Lnet/minecraft/util/math/noise/OctavePerlinNoiseSampler;Lnet/minecraft/util/math/noise/OctavePerlinNoiseSampler;Lnet/minecraft/util/math/noise/OctavePerlinNoiseSampler;DDDDD)V")
    private void setScaleAndFactorRange(OctavePerlinNoiseSampler lowerInterpolatedNoise, OctavePerlinNoiseSampler upperInterpolatedNoise, OctavePerlinNoiseSampler interpolationNoise, double xzScale, double yScale, double xzFactor, double yFactor, double smearScaleMultiplier, CallbackInfo ci) {
        SCALE_AND_FACTOR_RANGE = Codec.doubleRange(Double.MIN_VALUE, Double.MAX_VALUE);
        WorldGenOptions options = UltimateScaler.config;
        if (options == null)
            return;

        String xzScaleMultiplier = options.xzScaleMultiplier;
        xzScaleMultiplier = xzScaleMultiplier.replace(",", "");
        options.xzScaleMultiplier = xzScaleMultiplier;
        double multiplier;

        try {
            multiplier = Double.parseDouble(xzScaleMultiplier);
            this.xzScale = multiplier;
        } catch (NumberFormatException e) {
        }

        String yScaleMultiplier = options.yScaleMultiplier;
        yScaleMultiplier = yScaleMultiplier.replace(",", "");
        options.yScaleMultiplier = yScaleMultiplier;

        try {
            multiplier = Double.parseDouble(yScaleMultiplier);
            this.yScale = multiplier;
        } catch (NumberFormatException e) {
        }
    }

    @ModifyVariable(method = "sample", at = @At("STORE"), ordinal = 0)
    private double modifyBlockX(double x, DensityFunction.NoisePos pos) {
        // 修改 x 坐标
        return ((double) pos.blockX() * config.globalXScale + config.globalXOffset) * getScaledXzScale();
    }

    @ModifyVariable(method = "sample", at = @At("STORE"), ordinal = 1)
    private double modifyBlockY(double y, DensityFunction.NoisePos pos) {
        // 修改 y 坐标
        return ((double) pos.blockY() * config.globalYScale + config.globalYOffset) * getScaledYScale();
    }

    @ModifyVariable(method = "sample", at = @At("STORE"), ordinal = 2)
    private double modifyBlockZ(double z, DensityFunction.NoisePos pos) {
        // 修改 z 坐标
        return ((double) pos.blockZ() * config.globalZScale + config.globalZOffset) * getScaledXzScale();
    }

    @Accessor("scaledXzScale")
    abstract double getScaledXzScale();

    @Accessor("scaledYScale")
    abstract double getScaledYScale();
}
