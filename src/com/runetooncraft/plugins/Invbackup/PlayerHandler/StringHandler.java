package com.runetooncraft.plugins.Invbackup.PlayerHandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.runetooncraft.plugins.Invbackup.core.PagedString;

public class StringHandler {
	public static HashMap<String,PagedString> StringPages = new HashMap<String,PagedString>();
	
	public static void ParseBasicSringList(ArrayList<String> StringList, Player p) {
		for(String s : StringList) {
			p.sendMessage(ParseStringColors(s));
		}
	}
	public static String ParseStringColors(String s) {
		return s.replaceAll("&([0-9a-f])", "\u00A7$1");
	}
}
