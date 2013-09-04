package com.runetooncraft.plugins.Invbackup.PlayerHandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;

import com.runetooncraft.plugins.Invbackup.InvBackup;
import com.runetooncraft.plugins.Invbackup.Listeners.InvBackuplistener;
import com.runetooncraft.plugins.Invbackup.core.InventorySerializer;
import com.runetooncraft.plugins.Invbackup.core.Messenger;
import com.runetooncraft.plugins.Invbackup.core.PagedString;

public class PlayerHandler {
	public static HashMap<Player, Playerdata> PlayerDataMap = new HashMap<Player, Playerdata>();
	
	public static void InventorySaveLatest(Player p, String name) {
		Playerdata playerfile = PlayerHandler.PlayerDataMap.get(p);
			String invstring = InventorySerializer.tobase64(p.getInventory());
			String equipstring = InventorySerializer.tobase64(InventorySerializer.getArmorInventory(p.getInventory()));
			playerfile.setString("Backups.latest.TimeStamp", InvBackup.GetTimeStamp());
			playerfile.setString("Backups.latest.inv", invstring);
			playerfile.setString("Backups.latest.equip", equipstring);
	}
	public static void InventorySave(Player p, String name) {
		Playerdata playerfile = PlayerHandler.PlayerDataMap.get(p);
		int SaveNumber = playerfile.GetInt("Backups.selected");
			if(SaveNumber <= InvBackup.MaxSaves) {
				SaveNumber++;
			}else{
				SaveNumber = 1;
			}
		String invstring = InventorySerializer.tobase64(p.getInventory());
		String equipstring = InventorySerializer.tobase64(InventorySerializer.getArmorInventory(p.getInventory()));
		playerfile.setString("Backups." + SaveNumber + ".TimeStamp", InvBackup.GetTimeStamp());
		playerfile.setString("Backups." + SaveNumber + ".inv", invstring);
		playerfile.setString("Backups." + SaveNumber + ".equip", equipstring);
		playerfile.SetInt("Backups.selected", SaveNumber);
		int number = playerfile.GetInt("Backups.number");
		if(number <= InvBackup.MaxSaves) {
			number++;
		}
		playerfile.SetInt("Backups.number", number);
	}
	
	public static ArrayList<String> GetPlayerInvs(Player p) {
		ArrayList<String> PlayerInvs = new ArrayList<String>();
		if(PlayerDataMap.containsKey(p)) {
			Playerdata file = PlayerDataMap.get(p);
			int SaveNumber = file.GetInt("Backups.number");
			if(SaveNumber == 0) {
				PlayerInvs.set(0, "&cNo Player Inventories saved yet for this player.");
			}else{
				PlayerInvs.add("&aCurrent backups for player &b" + p.getName() + "&a:");
				for(int i = 1; i<=SaveNumber; i++) {
					String TimeStamp = file.GetString("Backups." + i + ".TimeStamp");
					PlayerInvs.add("&9Backup " + i + ": &6" + TimeStamp); // Backup 1: 08/11/2013 12:39:36
				}
				String name = p.getName();
				if(StringHandler.StringPages.containsKey(name)) {
					StringHandler.StringPages.remove(name);
				}
				StringHandler.StringPages.put(name, new PagedString(PlayerInvs, 10));
			}
		}
		return StringHandler.StringPages.get(p.getName()).GetFirstPage();
	}
	public static void OpenLivePlayerInventory(Player p, String string) {
		if(Bukkit.getPlayer(string) != null) {
			Inventory PlayerInv = Bukkit.createInventory(Bukkit.getPlayer(string), 45, string + "'s Inventory");
			Player GetPlayer = Bukkit.getPlayer(string);
			PlayerInv.setContents(GetPlayer.getInventory().getContents());
			EntityEquipment GetEquipment = GetPlayer.getEquipment();
			PlayerInv.setItem(41, GetEquipment.getHelmet());
			PlayerInv.setItem(42, GetEquipment.getChestplate());
			PlayerInv.setItem(43, GetEquipment.getLeggings());
			PlayerInv.setItem(44, GetEquipment.getBoots());
			InvBackuplistener.LiveInv.put(p.getName(), PlayerInv);
			InvBackuplistener.LiveInventoryEditor.put(string, PlayerInv);
			p.openInventory(PlayerInv);
		}else{
			p.sendMessage("Player " + string + " not found.");
		}
	}
	public static void OpenSavedInventory(Player p, String PlayerName, String Backup) {
		if(IsInteger(Backup)) {
			int BackupNumber = Integer.parseInt(Backup);
			if(InvBackup.config.getlist("Players.list").contains(PlayerName)) {
				Playerdata pd = null;
				if(!PlayerDataMap.containsValue(PlayerName)) PlayerDataMap.containsValue(PlayerName);
				pd = PlayerDataMap.get(Bukkit.getPlayer(PlayerName));
				if(pd.BackupExists(BackupNumber)) {
					p.openInventory(pd.GetBackup(p, BackupNumber));
				}else{
					Messenger.playermessage("Backup " + Backup + " does not exist for player " + PlayerName + ".", p);
				}
			}else{
				Messenger.playermessage("Player " + PlayerName + " not found in InvBackup.", p);
			}
		}else{
			Messenger.playermessage("3rd argument must be an Integer.", p);
		}
	}
	private static boolean IsInteger(String i) {
		Boolean ReturnBool = true;
		try{
			Integer.parseInt(i);
		}catch(NumberFormatException e) {
			ReturnBool = false;
		}
		
		return ReturnBool;
	}
	public static void RestoreInventory(Player p, String PlayerToRestore, String BackupString) {
		if(IsInteger(BackupString)) {
			int b = Integer.parseInt(BackupString);
			if(InvBackup.config.getlist("Players.list").contains(PlayerToRestore)) {
				Playerdata pd = null;
				if(!PlayerDataMap.containsValue(PlayerToRestore)) PlayerDataMap.containsValue(PlayerToRestore);
				pd = PlayerDataMap.get(Bukkit.getPlayer(PlayerToRestore));
				if(pd.BackupExists(b)) {
					pd.SetPlayerInventory(b);
					Messenger.playermessage("Inventory for player " + PlayerToRestore + " was reverted to backup number " + BackupString + ".", p);
					Messenger.playermessage("Your inventory was reverted to a backup by " + p.getName() + ".", Bukkit.getPlayer(PlayerToRestore));
				}else{
					Messenger.playermessage("Backup " + BackupString + " does not exist for player " + PlayerToRestore + ".", p);
					Messenger.playermessage("Use /ib ShowInvs " + PlayerToRestore + " to show the inventories of this player.", p);
				}
			}else{
				Messenger.playermessage("Player " + PlayerToRestore + " not found in InvBackup.", p);
			}
		}else{
			Messenger.playermessage("3rd argument must be an Integer.", p);
		}
	}
}
