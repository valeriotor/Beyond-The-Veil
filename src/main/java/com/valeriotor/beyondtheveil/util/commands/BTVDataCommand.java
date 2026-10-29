package com.valeriotor.beyondtheveil.util.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.*;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

import java.util.Collection;

public class BTVDataCommand {
    private static final SimpleCommandExceptionType ERROR_PLAYER_NOT_FOUND = new SimpleCommandExceptionType(Component.translatable("commands.btvdata.player_not_found"));
    private static final SimpleCommandExceptionType DIALOGUE_TYPE_NOT_FOUND = new SimpleCommandExceptionType(Component.translatable("commands.btvdata.dialogue_type_not_found"));
    private static final SimpleCommandExceptionType DIALOGUE_TEMPLATE_NOT_FOUND = new SimpleCommandExceptionType(Component.translatable("commands.btvdata.dialogue_template_not_found"));

    public static void register(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal("btvdata").requires((p_214560_) -> {
                    return p_214560_.hasPermission(2);
                })
                .then(Commands.argument("player", GameProfileArgument.gameProfile())
                        .then(Commands.literal("set")
                                .then(Commands.literal("boolean").then(Commands.argument("key", StringArgumentType.word()).then(Commands.argument("value", BoolArgumentType.bool()).executes(commandContext ->
                                        setBoolean(commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "player"), StringArgumentType.getString(commandContext, "key"), BoolArgumentType.getBool(commandContext, "value"))))))
                                .then(Commands.literal("int").then(Commands.argument("key", StringArgumentType.word()).then(Commands.argument("value", IntegerArgumentType.integer()).executes(commandContext ->
                                        setInt(commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "player"), StringArgumentType.getString(commandContext, "key"), IntegerArgumentType.getInteger(commandContext, "value"))))))
                                .then(Commands.literal("long").then(Commands.argument("key", StringArgumentType.word()).then(Commands.argument("value", LongArgumentType.longArg()).executes(commandContext ->
                                        setLong(commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "player"), StringArgumentType.getString(commandContext, "key"), LongArgumentType.getLong(commandContext, "value"))))))
                                .then(Commands.literal("string").then(Commands.argument("key", StringArgumentType.word()).then(Commands.argument("value", StringArgumentType.word()).executes(commandContext ->
                                        setString(commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "player"), StringArgumentType.getString(commandContext, "key"), StringArgumentType.getString(commandContext, "value"))))))
                                .then(Commands.literal("dialogue").then(Commands.argument("type", StringArgumentType.word()).then(Commands.argument("template", StringArgumentType.word()).executes(commandContext ->
                                        setDialogue(commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "player"), StringArgumentType.getString(commandContext, "type"), StringArgumentType.getString(commandContext, "template"))))))
                        )
                        .then(Commands.literal("query")
                                .then(Commands.literal("boolean").then(Commands.argument("key", StringArgumentType.word()).executes(commandContext ->
                                        queryBoolean(commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "player"), StringArgumentType.getString(commandContext, "key")))))
                                .then(Commands.literal("int").then(Commands.argument("key", StringArgumentType.word()).executes(commandContext ->
                                        queryInt(commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "player"), StringArgumentType.getString(commandContext, "key")))))
                                .then(Commands.literal("long").then(Commands.argument("key", StringArgumentType.word()).executes(commandContext ->
                                        queryLong(commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "player"), StringArgumentType.getString(commandContext, "key")))))
                                .then(Commands.literal("string").then(Commands.argument("key", StringArgumentType.word()).executes(commandContext ->
                                        queryString(commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "player"), StringArgumentType.getString(commandContext, "key")))))
                                .then(Commands.literal("dialogue").then(Commands.argument("type", StringArgumentType.word()).executes(commandContext ->
                                        queryDialogue(commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "player"), StringArgumentType.getString(commandContext, "type")))))
                        )
                ));
    }

    private static int setBoolean(CommandSourceStack pSource, Collection<GameProfile> gameProfiles, String key, boolean value) throws CommandSyntaxException {
        boolean flag = false;
        for (GameProfile profile : gameProfiles) {
            ServerPlayer serverplayer = pSource.getServer().getPlayerList().getPlayer(profile.getId());
            if (serverplayer != null) {
                DataUtil.setBooleanOnServerAndSync(serverplayer, key, value);
                flag = true;
            }
        }
        if (flag) {
            return 1;
        }
        throw ERROR_PLAYER_NOT_FOUND.create();
    }

    private static int setInt(CommandSourceStack pSource, Collection<GameProfile> gameProfiles, String key, int value) throws CommandSyntaxException {
        boolean flag = false;
        for (GameProfile profile : gameProfiles) {
            ServerPlayer serverplayer = pSource.getServer().getPlayerList().getPlayer(profile.getId());
            if (serverplayer != null) {
                DataUtil.setIntOnServerAndSync(serverplayer, key, value, false);
                flag = true;
            }
        }
        if (flag) {
            return 1;
        }
        throw ERROR_PLAYER_NOT_FOUND.create();
    }

    private static int setLong(CommandSourceStack pSource, Collection<GameProfile> gameProfiles, String key, long value) throws CommandSyntaxException {
        boolean flag = false;
        for (GameProfile profile : gameProfiles) {
            ServerPlayer serverplayer = pSource.getServer().getPlayerList().getPlayer(profile.getId());
            if (serverplayer != null) {
                DataUtil.setLongOnServerAndSync(serverplayer, key, value, false);
                flag = true;
            }
        }
        if (flag) {
            return 1;
        }
        throw ERROR_PLAYER_NOT_FOUND.create();
    }

    private static int setString(CommandSourceStack pSource, Collection<GameProfile> gameProfiles, String key, String value) throws CommandSyntaxException {
        boolean flag = false;
        for (GameProfile profile : gameProfiles) {
            ServerPlayer serverplayer = pSource.getServer().getPlayerList().getPlayer(profile.getId());
            if (serverplayer != null) {
                DataUtil.setStringOnServerAndSync(serverplayer, key, value, false);
                flag = true;
            }
        }
        if (flag) {
            return 1;
        }
        throw ERROR_PLAYER_NOT_FOUND.create();
    }

    private static int setDialogue(CommandSourceStack pSource, Collection<GameProfile> gameProfiles, String type, String template) throws CommandSyntaxException {
        boolean flag = false;
        try {
            DialogueType dialogueType = DialogueType.valueOf(type.toUpperCase());
            DialogueTemplate template1 = DialogueRegistry.getTemplate(dialogueType, template);
            if (template1 != null) {
                for (GameProfile profile : gameProfiles) {
                    ServerPlayer serverplayer = pSource.getServer().getPlayerList().getPlayer(profile.getId());
                    if (serverplayer != null) {
                        DialogueData.for_(serverplayer).setDialogue(dialogueType, template1);
                        flag = true;
                    }
                }

            } else {
                throw DIALOGUE_TEMPLATE_NOT_FOUND.create();
            }
        } catch (IllegalArgumentException e) {
            throw DIALOGUE_TYPE_NOT_FOUND.create();
        }
        if (flag) {
            return 1;
        }
        throw ERROR_PLAYER_NOT_FOUND.create();
    }

    private static int queryBoolean(CommandSourceStack pSource, Collection<GameProfile> gameProfiles, String key) throws CommandSyntaxException {
        boolean flag = false;
        for (GameProfile profile : gameProfiles) {
            ServerPlayer serverplayer = pSource.getServer().getPlayerList().getPlayer(profile.getId());
            if (serverplayer != null) {
                boolean value = DataUtil.getBoolean(serverplayer, key);
                pSource.sendSuccess(() -> Component.literal(String.valueOf(value)), false);
                flag = true;
            }
        }
        if (flag) {
            return 1;
        }
        throw ERROR_PLAYER_NOT_FOUND.create();
    }

    private static int queryInt(CommandSourceStack pSource, Collection<GameProfile> gameProfiles, String key) throws CommandSyntaxException {
        boolean flag = false;
        for (GameProfile profile : gameProfiles) {
            ServerPlayer serverplayer = pSource.getServer().getPlayerList().getPlayer(profile.getId());
            if (serverplayer != null) {
                if (DataUtil.hasInt(serverplayer, key)) {
                    int value = DataUtil.getInt(serverplayer, key);
                    pSource.sendSuccess(() -> Component.literal(String.valueOf(value)), false);
                } else {
                    pSource.sendSuccess(() -> Component.literal("Not found"), false);
                }
                flag = true;
            }
        }
        if (flag) {
            return 1;
        }
        throw ERROR_PLAYER_NOT_FOUND.create();
    }

    private static int queryLong(CommandSourceStack pSource, Collection<GameProfile> gameProfiles, String key) throws CommandSyntaxException {
        boolean flag = false;
        for (GameProfile profile : gameProfiles) {
            ServerPlayer serverplayer = pSource.getServer().getPlayerList().getPlayer(profile.getId());
            if (serverplayer != null) {
                if (DataUtil.hasLong(serverplayer, key)) {
                    long value = DataUtil.getLong(serverplayer, key);
                    pSource.sendSuccess(() -> Component.literal(String.valueOf(value)), false);
                } else {
                    pSource.sendSuccess(() -> Component.literal("Not found"), false);
                }
                flag = true;
            }
        }
        if (flag) {
            return 1;
        }
        throw ERROR_PLAYER_NOT_FOUND.create();
    }

    private static int queryString(CommandSourceStack pSource, Collection<GameProfile> gameProfiles, String key) throws CommandSyntaxException {
        boolean flag = false;
        for (GameProfile profile : gameProfiles) {
            ServerPlayer serverplayer = pSource.getServer().getPlayerList().getPlayer(profile.getId());
            if (serverplayer != null) {
                if (DataUtil.hasString(serverplayer, key)) {
                    String value = DataUtil.getString(serverplayer, key);
                    pSource.sendSuccess(() -> Component.literal(value), false);
                } else {
                    pSource.sendSuccess(() -> Component.literal("Not found"), false);
                }
                flag = true;
            }
        }
        if (flag) {
            return 1;
        }
        throw ERROR_PLAYER_NOT_FOUND.create();
    }

    private static int queryDialogue(CommandSourceStack pSource, Collection<GameProfile> gameProfiles, String type) throws CommandSyntaxException {
        boolean flag = false;
        try {
            DialogueType dialogueType = DialogueType.valueOf(type.toUpperCase());
            for (GameProfile profile : gameProfiles) {
                ServerPlayer serverplayer = pSource.getServer().getPlayerList().getPlayer(profile.getId());
                if (serverplayer != null) {
                    DialogueTemplate dialogue = DialogueData.for_(serverplayer).getDialogue(dialogueType);
                    if (dialogue != null) {
                        pSource.sendSuccess(() -> Component.literal(dialogue.getID()), false);
                    } else {
                        pSource.sendSuccess(() -> Component.literal("Not found"), false);
                    }
                    flag = true;
                }
            }
        } catch (IllegalArgumentException e) {
            throw DIALOGUE_TYPE_NOT_FOUND.create();
        }

        if (flag) {
            return 1;
        }
        throw ERROR_PLAYER_NOT_FOUND.create();
    }
}
