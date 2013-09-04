package com.runetooncraft.plugins.Invbackup;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.runetooncraft.plugins.Invbackup.PlayerHandler.PlayerHandler;
import com.runetooncraft.plugins.Invbackup.core.Messenger;

public class InvBackupTimer extends BukkitRunnable {

	@Override
	public void run() {
		Player[] players = Bukkit.getOnlinePlayers();
		Messenger.info("InvBackup Commencing.");
		for(Player p : players) {
			PlayerHandler.InventorySaveLatest(p, p.getName());
			PlayerHandler.InventorySave(p, p.getName());
		}
		Messenger.info("InvBackup Finished.");
	}

}
