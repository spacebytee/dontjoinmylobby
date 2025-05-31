package com.bytespacegames.dontjoinmylobby.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

import java.util.ArrayList;
import java.util.List;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CommandManager {
    public static CommandManager INSTANCE;
    private List<Command> registeredCommands = new ArrayList<Command>();
    public CommandManager() {
        INSTANCE = this;
    }
    public List<Command> getRegisteredCommands() {
        return registeredCommands;
    }
    public void registerCommand(Command c) {
        /*CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(net.minecraft.server.command.CommandManager.literal(c.getName())
                .executes(context -> { c.execute(context); return 1; })));*/
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            final LiteralCommandNode<FabricClientCommandSource> node = dispatcher.register(
                    literal(c.getName())
                            .executes(c::execute)
                            .then(ClientCommandManager.argument("args", StringArgumentType.greedyString()).executes(c::execute))
            );
        });
        registeredCommands.add(c);
    }
}