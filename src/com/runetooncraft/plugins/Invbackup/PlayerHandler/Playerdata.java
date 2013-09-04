package com.runetooncraft.plugins.Invbackup.PlayerHandler;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;

import com.runetooncraft.plugins.Invbackup.core.InventorySerializer;
import com.runetooncraft.plugins.Invbackup.core.Messenger;

public class Playerdata {
	
	private YamlConfiguration Playerdata;
	private File Playerdatafile;
	private String playername;

	public Playerdata(File Playerdatafile,String playername) {
		this.Playerdata = new YamlConfiguration();
		this.Playerdatafile = Playerdatafile;
		this.playername = playername;
	}
	
	public boolean load() {
		try {
		if (!Playerdatafile.exists()) {
				Playerdatafile.createNewFile();
				Messenger.info("New Playerdata file created for " + playername);
				loaddefaults();
		}
			Playerdata.load(Playerdatafile);
			return true;
		}catch(Exception e) {
			Messenger.severe("Playerdata file for Player " + playername + " failed to load. Error returned:\n" + e.getMessage());
			return false;
		}
	}
	public boolean save() {
		try {
			Playerdata.save(Playerdatafile);
		} catch (Exception e) {
			Messenger.severe("Playerdata file for Player " + playername + " failed to save. Error returned:\n" + e.getMessage());
		}
		return true;
	}

	private void loaddefaults() {
		Playerdata.addDefault("Player.name", playername);
		Playerdata.addDefault("Backups.number", 0);
		Playerdata.addDefault("Backups.selected", 0);
		Playerdata.options().copyDefaults(true);
		save();
	}
	public int GetInt(String path) {
		return Playerdata.getInt(path);
	}
	public String GetString(String path) {
		return Playerdata.getString(path);
	}
	public void setString(String path, String item){
		Playerdata.set(path, item);
		save();
	}
	public boolean BackupExists(int Backup) {
		if(Playerdata.get("Backups." + Backup + ".TimeStamp") == null) {
			return false;
		}else{
			return true;
		}
	}
	public Inventory GetBackup(Player InventoryHolder, int Backup) {
		Inventory ReturnInventory = null;
		if(BackupExists(Backup)) {
			ReturnInventory = Bukkit.createInventory(InventoryHolder, 45, "Player: " + playername + " Backup: " + Backup);
			ReturnInventory.setContents(InventorySerializer.frombase64(GetString("Backups." + Backup + ".inv")).getContents());
			Inventory EquipInventory = InventorySerializer.frombase64(GetString("Backups." + Backup + ".equip"));
			if(EquipInventory.getItem(0) != null) ReturnInventory.setItem(41, EquipInventory.getItem(0));
			if(EquipInventory.getItem(1) != null) ReturnInventory.setItem(42, EquipInventory.getItem(1));
			if(EquipInventory.getItem(2) != null) ReturnInventory.setItem(43, EquipInventory.getItem(2));
			if(EquipInventory.getItem(3) != null) ReturnInventory.setItem(44, EquipInventory.getItem(3));
		}
		return ReturnInventory;
	}
	public void SetPlayerInventory(int Backup) {
		if(BackupExists(Backup)) {
			Player player = Bukkit.getPlayer(playername);
			player.getInventory().setContents(InventorySerializer.frombase64(GetString("Backups." + Backup + ".inv")).getContents());
			Inventory EquipInventory = InventorySerializer.frombase64(GetString("Backups." + Backup + ".equip"));
			player.getEquipment().setHelmet(EquipInventory.getItem(3));
			player.getEquipment().setChestplate(EquipInventory.getItem(2));
			player.getEquipment().setLeggings(EquipInventory.getItem(1));
			player.getEquipment().setBoots(EquipInventory.getItem(0));
		}
	}
	public void SetInt(String path, int item){
		Playerdata.set(path, item);
		save();
	}
}


