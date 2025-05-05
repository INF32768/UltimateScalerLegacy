package me.inf32768.ultimatescaler.commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.math.BigDecimal;
import java.math.BigInteger;

import static net.minecraft.server.command.CommandManager.argument;

public class CalculateOffset {
    public static final SimpleCommandExceptionType SCALE_ZERO_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("command.exception.scale_zero"));
    public static final SimpleCommandExceptionType NOT_FOUND_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("command.exception.not_found"));
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("caloffset")
            .then(argument("originalPos", StringArgumentType.string())
                .then(argument("scale", DoubleArgumentType.doubleArg())
                    .then(argument("offset", DoubleArgumentType.doubleArg())
                        .executes(context -> (int) calculate(StringArgumentType.getString(context, "originalPos"), DoubleArgumentType.getDouble(context, "scale"), DoubleArgumentType.getDouble(context, "offset"), context)))))));
    }

    public static double calculate(String originalPos, double scale, double offset, CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (scale == 0) {
            throw SCALE_ZERO_EXCEPTION.create();
        }
        // BigDecimal常量s,o,p
        BigDecimal p = new BigDecimal(originalPos);
        BigDecimal s = new BigDecimal(scale);
        BigDecimal o = new BigDecimal(offset);

        // 初始化二分查找的上下界
        BigInteger low = new BigInteger("-1000000000000000000000");
        BigInteger high = low.negate(); // 假设一个足够大的上限

        // 使用二分查找来找到满足条件的x的最小值
        while (low.compareTo(high) < 0) {
            BigInteger mid = low.add(high).divide(BigInteger.valueOf(2));
            BigDecimal midBigDecimal = new BigDecimal(mid);

            // 计算mid * s + o
            double resultDouble = midBigDecimal.doubleValue() * s.doubleValue() + o.doubleValue();
            BigDecimal result = new BigDecimal(resultDouble);

            // 检查是否满足result >= p
            if (result.compareTo(p) >= 0) {
                high = mid; // mid满足条件，尝试更小的x
            } else {
                low = mid.add(BigInteger.ONE); // mid不满足条件，尝试更大的x
            }
        }

        // 确保low是满足条件的最小值
        BigDecimal lowBigDecimal = new BigDecimal(low);
        BigDecimal result = lowBigDecimal.multiply(s).add(o);
        if (result.compareTo(p) < 0) {
            low = low.add(BigInteger.ONE);
        }
        if (low.compareTo(high) > 0) {
            throw NOT_FOUND_EXCEPTION.create();
        }
        BigInteger finalLow = low;
        context.getSource().sendFeedback(() -> Text.literal("The offset position is: %s".formatted(finalLow.toString())), false);
        return low.doubleValue();
    }
}
