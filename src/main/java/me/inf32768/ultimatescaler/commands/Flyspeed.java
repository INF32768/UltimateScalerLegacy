package me.inf32768.ultimatescaler.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;

import static net.minecraft.server.command.CommandManager.argument;

public class Flyspeed {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(CommandManager.literal("flyspeed").requires((commandSource) -> commandSource.hasPermissionLevel(2)).then(argument("level", FloatArgumentType.floatArg()).executes((commandContext) -> {
                PlayerEntity player = commandContext.getSource().getPlayer();
                if (player != null) {
                    player.getAbilities().setFlySpeed(FloatArgumentType.getFloat(commandContext, "level") * 0.05f);
                    player.sendAbilitiesUpdate();
                }
            return 1;
            }))));
    }
}
