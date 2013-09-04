package com.runetooncraft.plugins.Invbackup.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.world.SpawnChangeEvent;

import com.runetooncraft.plugins.Invbackup.PlayerHandler.PlayerHandler;
import com.runetooncraft.plugins.Invbackup.PlayerHandler.StringHandler;
import com.runetooncraft.plugins.Invbackup.core.Config;
import com.runetooncraft.plugins.Invbackup.core.Messenger;

public class InvCommandListener implements CommandExecutor {
	Config config;
	public InvCommandListener (Config config) {
		this.config = config;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("ib") || commandLabel.equalsIgnoreCase("invbackup")) {
		if(args.length >= 1) {
			Player p = (Player) sender;
			String argument = args[0];
			if(argument.equalsIgnoreCase("Showinventories") || argument.equalsIgnoreCase("showinvs")) {
				if(p.hasPermission("invbackup.showinventories")) {
					if(args.length == 2) {
						String PlayerName = args[1];
						if(Bukkit.getPlayer(PlayerName) != null) {
							Player player = Bukkit.getPlayer(PlayerName);
							StringHandler.ParseBasicSringList(PlayerHandler.GetPlayerInvs(player), p);
						}else{
							Messenger.playermessage("Player not found: " + PlayerName, p);
						}
					}else{
						Messenger.playermessage("Usage: /ib ShowInvs <Player>", p);
					}
				}else{
					Messenger.MissingPermission(p);
				}
			}else if(argument.equalsIgnoreCase("page") || argument.equalsIgnoreCase("p")) {
				if(args.length == 2) {
					if(StringHandler.StringPages.containsKey(p.getName())) {
						if(StringHandler.StringPages.get(p.getName()) != null && StringHandler.StringPages.get(p.getName()).ContaingsPage(args[1])) {
							StringHandler.ParseBasicSringList(StringHandler.StringPages.get(p.getName()).GetPage(args[1]), p);
						}
					}else{
						Messenger.playermessage("You currently do not have any Paged message.", p);
					}
				}
			}else if(argument.equalsIgnoreCase("openplayerinventory") || argument.equalsIgnoreCase("pinventory") || argument.equalsIgnoreCase("playerinventory") || argument.equalsIgnoreCase("pinv")) {
				if(p.hasPermission("invbackup.openliveinventory")) {
					if(args.length == 2) {
						PlayerHandler.OpenLivePlayerInventory(p, args[1]);
					}else{
						Messenger.playermessage("Usage: /ib pinv <Player>", p);
					}
				}else{
					Messenger.MissingPermission(p);
				}
			}else if(argument.equalsIgnoreCase("openinv") || argument.equalsIgnoreCase("openinventory")) {
				if(p.hasPermission("invbackup.opensavedinventory")) {
					if(args.length == 3) {
						PlayerHandler.OpenSavedInventory(p, args[1], args[2]);
					}else{
						Messenger.playermessage("Usage: /ib openinv <Player> <BackupNumber>", p);
					}
				}else{
					Messenger.MissingPermission(p);
				}
			}else if(argument.equalsIgnoreCase("restore")) {
				if(p.hasPermission("invbackup.restoreinventories")) {
					if(args.length == 3) {
						PlayerHandler.RestoreInventory(p, args[1], args[2]);
					}else{
						Messenger.playermessage("Usage: /ib restore <Player> <BackupNumber>", p);
					}
				}else{
					Messenger.MissingPermission(p);
				}	
			}
			return true;
		}else{
			//TODO: Usage statement
		}
		}
		return false;
	}

}
