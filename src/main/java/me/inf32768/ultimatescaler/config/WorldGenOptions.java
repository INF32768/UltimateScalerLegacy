package me.inf32768.ultimatescaler.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "ultimatescaler")
public class WorldGenOptions implements ConfigData {
    public boolean enableFarlands;
    public boolean shardFarlands;
    public int genOffset;
    public double xzCoordinateScale = 684.412;
    public double yCoordinateScale = 684.412;
    public String xzScaleMultiplier = "default";
    public String yScaleMultiplier = "default";
    public double globalXScale = 1.0;
    public double globalZScale = 1.0;
    public double globalYScale = 1.0;
    public double globalXOffset = 0.0;
    public double globalZOffset = 0.0;
    public double globalYOffset = 0.0;

    @Override
    public void validatePostLoad() throws ValidationException {
        try {
            Double.parseDouble(xzScaleMultiplier);
        } catch (NumberFormatException ignored) {
            xzScaleMultiplier = "default";
        }

        try {
            Double.parseDouble(yScaleMultiplier);
        } catch (NumberFormatException ignored) {
            yScaleMultiplier = "default";
        }
    }
}
