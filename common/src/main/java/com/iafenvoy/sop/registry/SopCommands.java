package com.iafenvoy.sop.registry;

import com.iafenvoy.sop.SongsOfPower;
import com.iafenvoy.sop.item.SongCubeItem;
import com.iafenvoy.sop.power.SongPowerData;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class SopCommands {
    public static void init() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> dispatcher
                .register(CommandManager.literal("songpower")
                        .requires(ServerCommandSource::isExecutedByPlayer)
                        .then(CommandManager.literal("use")
                                .executes(SopCommands::useSong)
                        ).then(CommandManager.literal("replace")
                                .executes(SopCommands::replaceSong)
                        )
                ));
    }

    public static int useSong(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        PlayerEntity player = source.getPlayerOrThrow();
        ItemStack stack = player.getMainHandStack();
        if (stack.getItem() instanceof SongCubeItem songCube) {
            SongPowerData data = SongPowerData.byPlayer(player);
            SongPowerData.SinglePowerData singlePowerData = data.get(songCube.getType());
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
        if (stack.getItem() instanceof SongCubeItem songCube) {
            SongPowerData data = SongPowerData.byPlayer(player);
            SongPowerData.SinglePowerData singlePowerData = data.get(songCube.getType());
            player.setStackInHand(Hand.MAIN_HAND, singlePowerData.pickHoldItem());
            singlePowerData.setHoldItem(stack);
            source.sendFeedback(() -> Text.translatable("command." + SongsOfPower.MOD_ID + ".use_song.success"), false);
            return 1;
        }
        source.sendFeedback(() -> Text.translatable("command." + SongsOfPower.MOD_ID + ".use_song.invalid"), false);
        return 0;
    }
}
