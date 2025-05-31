package com.bytespacegames.dontjoinmylobby.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

public abstract class Command {
    private final String name;

    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract int onCall(FabricClientCommandSource source, String[] arguments);

    public int execute(CommandContext<FabricClientCommandSource> context) {
        String[] arguments = extractArguments(context);
        return onCall(context.getSource(), arguments);
    }

    private String[] extractArguments(CommandContext<FabricClientCommandSource> context) {
        try {
            String args = StringArgumentType.getString(context, "args");
            return args.split(" ");
        } catch (IllegalArgumentException e) {
            return new String[0];
        }
    }
}