package com.bytespacegames.dontjoinmylobby;

import com.bytespacegames.dontjoinmylobby.command.CommandManager;
import com.bytespacegames.dontjoinmylobby.command.impl.DontJoinMyLobbyCmd;
import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class DontJoinMyLobby implements ClientModInitializer {
	public static final String MOD_ID = "dontjoinmylobby";
	private static final String PREFIX = "§cDontJoinMyLobby §e> ";
	public static DontJoinMyLobby INSTANCE;
	private boolean enabled = true;
	private boolean hypixelOnly = true;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitializeClient() {
		INSTANCE = this;
		new CommandManager();
		CommandManager.INSTANCE.registerCommand(new DontJoinMyLobbyCmd());
		load();
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void toggleMod() {
		enabled = !enabled;
	}
	public boolean isHypixelOnly() {
		return hypixelOnly;
	}
	public void toggleHypixel() {
		hypixelOnly = !hypixelOnly;
		save();
	}
	private static final String[] HYPIXEL_IPS = { "hypixel.io", "hypixel.net"};
	public boolean isOnHypixel() {
		if (Minecraft.getInstance().getCurrentServer() == null) return false;
		String ip = Minecraft.getInstance().getCurrentServer().ip;
		for (String s : HYPIXEL_IPS) {
			if (ip.contains(s)) return true;
		}
		return false;
	}
	public void save() {
		File CONFIG_FILE = new File(Minecraft.getInstance().gameDirectory, "config/dontjoinmylobby.config");
		try {
			if (!CONFIG_FILE.getParentFile().exists()) {
				CONFIG_FILE.getParentFile().mkdirs();
			}

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE))) {
				writer.write("enabled:" + isEnabled() + "\n");
				writer.write("hypixelonly:" + isHypixelOnly() + "\n");
			}
		} catch (IOException e) {
			//noinspection CallToPrintStackTrace
			e.printStackTrace();
		}
	}
	public void load() {
		File CONFIG_FILE = new File(Minecraft.getInstance().gameDirectory, "config/dontjoinmylobby.config");
		if (!CONFIG_FILE.exists()) {
			return;
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
			String s;
			while ((s = reader.readLine()).contains(":")) {
				String[] args = s.split(":",2);
				if (args.length < 2) continue;
				boolean val = Boolean.parseBoolean(args[1]);
				switch (args[0]) {
					case "enabled":
						enabled = val;
						break;
					case "hypixelonly":
						hypixelOnly = val;
						break;
				}
			}
		} catch (Exception e) {
            e.printStackTrace();
        }
    }
	public void displayMessage(String s) {
		Minecraft.getInstance().gui.getChat().addMessage(Component.literal(PREFIX + s));
	}
}