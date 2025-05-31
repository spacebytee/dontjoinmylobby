package com.bytespacegames.dontjoinmylobby.command.impl;

import com.bytespacegames.dontjoinmylobby.DontJoinMyLobby;
import com.bytespacegames.dontjoinmylobby.command.Command;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class DontJoinMyLobbyCmd extends Command {
    public DontJoinMyLobbyCmd() {
        super("dontjoinmylobby");
    }

    @Override
    public int onCall(FabricClientCommandSource source, String[] arguments) {
        if (arguments.length == 0) {
            DontJoinMyLobby.INSTANCE.toggleMod();
            if (DontJoinMyLobby.INSTANCE.isEnabled()) {
                DontJoinMyLobby.INSTANCE.displayMessage("Join messages will now be hidden in lobbies.");
            } else {
                DontJoinMyLobby.INSTANCE.displayMessage("Join messages will now be shown in lobbies.");
            }
            return 1;
        }
        if (arguments[0].equalsIgnoreCase("hypixelonly")) {
            DontJoinMyLobby.INSTANCE.toggleHypixel();
            if (DontJoinMyLobby.INSTANCE.isHypixelOnly()) {
                DontJoinMyLobby.INSTANCE.displayMessage("The mod will now only be active on hypixel.");
            } else {
                DontJoinMyLobby.INSTANCE.displayMessage("The mod will now be active on all servers.");
            }
            return 1;
        }
        DontJoinMyLobby.INSTANCE.displayMessage("Sorry, I don't know what you're trying to do! :(");
        return 1;
    }
}
