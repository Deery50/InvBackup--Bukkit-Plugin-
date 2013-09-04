package com.runetooncraft.plugins.Invbackup;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.runetooncraft.plugins.Invbackup.Listeners.InvBackuplistener;
import com.runetooncraft.plugins.Invbackup.Listeners.InvCommandListener;
import com.runetooncraft.plugins.Invbackup.PlayerHandler.PlayerHandler;
import com.runetooncraft.plugins.Invbackup.PlayerHandler.Playerdata;
import com.runetooncraft.plugins.Invbackup.core.Config;
import com.runetooncraft.plugins.Invbackup.core.InventorySerializer;
import com.runetooncraft.plugins.Invbackup.core.Messenger;

public class InvBackup extends JavaPlugin {
	public static Config config = null;
	public static SimpleDateFormat dateFormat = null;
	public static int MaxSaves = 0;
	public void onEnable() {
		loadconfig();
		Messenger m = new Messenger();
		getCommand("ib").setExecutor(new InvCommandListener(config));
		getCommand("invbackup").setExecutor(new InvCommandListener(config));
		getServer().getPluginManager().registerEvents(new InvBackuplistener(config), this);
		loadtimer();
		dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); //  8/11/2013 12:19:20
		MaxSaves = config.getint("InvBackup.MaxNumberOfSaves");
	}
	public static String GetTimeStamp() {
		Date date = new Date();
		return dateFormat.format(date);
	}
	private void loadtimer() {
		int i = config.getint("InvBackup.SaveIntervalMinutes");
		int w = i * 1200;
		new InvBackupTimer().runTaskTimer(this, w, w);
	}

	public void loadconfig() {
		File dir = this.getDataFolder();
		if(!dir.exists()) dir.mkdir();
		File file = new File(dir, "config.yml");
		config = new Config(file);
		if (!config.load()) {
			this.getServer().getPluginManager().disablePlugin(this);
			throw new IllegalStateException("The config was not loaded correctly!");
		}
	}
	public static void playerdataload(String playername, Player p) {
		File dir = new File(Bukkit.getPluginManager().getPlugin("InvBackup").getDataFolder() + "/Playerdata");
		if(!dir.exists()) dir.mkdir();
		File file = new File(dir, playername + ".yml");
		Playerdata playerdata = new Playerdata(file, playername);
		if (!playerdata.load()) {
			throw new IllegalStateException("The player data file for player " + playername + " was not loaded correctly.");
		}else{
			PlayerHandler.PlayerDataMap.put(p, playerdata);
			config.addtolist("Players.list", playername);
		}
	}
}
