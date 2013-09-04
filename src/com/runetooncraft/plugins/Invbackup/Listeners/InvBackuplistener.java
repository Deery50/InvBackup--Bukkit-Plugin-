package com.runetooncraft.plugins.Invbackup.Listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.runetooncraft.plugins.Invbackup.InvBackup;
import com.runetooncraft.plugins.Invbackup.core.Config;
import com.runetooncraft.plugins.Invbackup.core.Messenger;

public class InvBackuplistener implements Listener {
	Config config;
	public static HashMap<String,Inventory> LiveInv = new HashMap<String,Inventory>();
	public static HashMap<String,Inventory> LiveInventoryEditor = new HashMap<String, Inventory>();
	public InvBackuplistener(Config config) {
		this.config = config;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		String playername = p.getName();
		InvBackup.playerdataload(playername, p);
	}
/*	@EventHandler
	public void OnInventoryClick(InventoryClickEvent event) {
		Messenger.info("Clicked slot:" + event.getSlot());
		if(LiveInv.containsKey(event.getWhoClicked().getName())) {
		if(LiveInv.get(event.getWhoClicked().getName()).equals(event.getInventory().getName())) {
			ItemStack is = CheckClickType(event);
			Inventory i = LiveInv.get(event.getWhoClicked().getName());
			Player LiveInventoryHolder = (Player) i.getHolder();
			Inventory LiveInventory = LiveInventoryHolder.getInventory();
			if(event.getSlot() < 45) {
				Messenger.info("Current item = " + is.getTypeId());
				LiveInventory.setItem(event.getSlot(), is);
			}else if(event.getSlot() == 41) {
				LiveInventoryHolder.getEquipment().setHelmet(is);
			}else if(event.getSlot() == 42) {
				LiveInventoryHolder.getEquipment().setChestplate(is);
			}else if(event.getSlot() == 43) {
				LiveInventoryHolder.getEquipment().setLeggings(is);
			}else if(event.getSlot() == 44) {
				LiveInventoryHolder.getEquipment().setBoots(is);
			}
		}
		}else if(LiveInventoryEditor.containsKey(event.getWhoClicked().getName())) {
			Inventory i = LiveInventoryEditor.get(event.getWhoClicked().getName());
			Inventory LiveInventory = event.getInventory();
			ItemStack is = CheckClickType(event);
			i.setItem(event.getSlot(), is);
		}
	}
	private ItemStack CheckClickType(InventoryClickEvent event) {
		if(event.getAction().equals(InventoryAction.COLLECT_TO_CURSOR)) {
			Messenger.info("Collect to cursor type");
			return event.getCursor();
		}else{
			return event.getInventory().getItem(event.getSlot());
		}
	}
	@EventHandler
	public void OnInventoryClose(InventoryCloseEvent event) {
		if(LiveInv.containsKey(event.getPlayer().getName())) {
			Player p = (Player) LiveInv.get(event.getPlayer()).getHolder();
			String name = p.getName();
			LiveInventoryEditor.remove(name);
			LiveInv.remove(event.getPlayer().getName());
		}
	}*/
}
