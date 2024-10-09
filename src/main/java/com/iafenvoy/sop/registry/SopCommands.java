package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.item.block.AbstractSongCubeBlock;
import com.iafenvoy.sop.power.SongPowerData;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

import java.util.Collection;

public final class SopCommands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> dispatcher
                .register(CommandManager.literal("songpower")
                        .requires(ServerCommandSource::isExecutedByPlayer)
                        .then(CommandManager.literal("use")
                                .executes(SopCommands::useSong)
                        ).then(CommandManager.literal("replace")
                                .executes(SopCommands::replaceSong)
                        ).then(CommandManager.literal("enable")
                                .requires(source -> source.hasPermissionLevel(selection.dedicated ? 4 : 0))
                                .then(CommandManager.argument("players", EntityArgumentType.players())
                                        .executes(SopCommands::enableSong)
                                )
                        ).then(CommandManager.literal("disable")
                                .requires(source -> source.hasPermissionLevel(selection.dedicated ? 4 : 0))
                                .then(CommandManager.argument("players", EntityArgumentType.players())
                                        .executes(SopCommands::disableSong)
                                )
                        )
                ));
    }

    public static int useSong(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        PlayerEntity player = source.getPlayerOrThrow();
        ItemStack stack = player.getMainHandStack();
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractSongCubeBlock songCube) {
            SongPowerData data = SongPowerData.byPlayer(player);
            SongPowerData.SinglePowerData singlePowerData = data.get(songCube.getCategory());
            if (singlePowerData.getHoldItem().isEmpty()) {
                singlePowerData.setHoldItem(stack);
                player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                source.sendFeedback(() -> Text.translatable("command." + SongsOfPower.MOD_ID + ".use_song.success"), false);
                return 1;
            }
            source.sendFeedback(() -> Text.translatable("command." + SongsOfPower.MOD_ID + ".use_song.full"), false);
            return 0;
        }
        source.sendFeedback(() -> Text.translatable("command." + SongsOfPower.MOD_ID + ".use_song.invalid"), false);
        return 0;
    }

    public static int replaceSong(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        PlayerEntity player = source.getPlayerOrThrow();
        ItemStack stack = player.getMainHandStack();
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractSongCubeBlock songCube) {
            SongPowerData data = SongPowerData.byPlayer(player);
            SongPowerData.SinglePowerData singlePowerData = data.get(songCube.getCategory());
            player.setStackInHand(Hand.MAIN_HAND, singlePowerData.pickHoldItem());
            singlePowerData.setHoldItem(stack);
            source.sendFeedback(() -> Text.translatable("command." + SongsOfPower.MOD_ID + ".use_song.success"), false);
            return 1;
        }
        source.sendFeedback(() -> Text.translatable("command." + SongsOfPower.MOD_ID + ".use_song.invalid"), false);
        return 0;
    }

    public static int enableSong(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "players");
        for (ServerPlayerEntity player : players) SongPowerData.byPlayer(player).enable();
        context.getSource().sendFeedback(() -> Text.literal("Success!"), true);
        return 1;
    }

    public static int disableSong(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "players");
        for (ServerPlayerEntity player : players) SongPowerData.byPlayer(player).disable();
        context.getSource().sendFeedback(() -> Text.literal("Success!"), true);
        return 1;
    }
}
