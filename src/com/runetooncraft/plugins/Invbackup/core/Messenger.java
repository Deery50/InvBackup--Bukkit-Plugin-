package com.runetooncraft.plugins.Invbackup.core;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messenger {

	private static final Logger log = Logger.getLogger("Minecraft");
	private static final String prefix = "[InvBackup] ";
	private static final String colorprefix = (ChatColor.RED + "[InvBackup] " + ChatColor.GREEN);
	
	public static void severe(String msg) {
		log.severe(prefix + msg);
	}
	public static void info(String msg) {
		log.info(prefix + msg);
	}
	public static void broadcast(String msg) {
		Bukkit.broadcastMessage(colorprefix + msg);
	}
	public static void playermessage(String msg, Player p) {
		p.sendMessage(colorprefix + msg);
	}
	public static void MissingPermission(Player p) {
		playermessage("You do not have permission for this command.", p);
	}
	
}
