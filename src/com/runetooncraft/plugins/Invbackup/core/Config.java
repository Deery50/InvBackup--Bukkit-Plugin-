package com.runetooncraft.plugins.Invbackup.core;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;


public class Config {
	private YamlConfiguration config;
	private File configFile;
	
	public Config(File configfile) {
		this.config = new YamlConfiguration();
		this.configFile = configfile;
	}
	public boolean load() {
		try {
			if (!configFile.exists()) {
				configFile.createNewFile();
				loaddefaults();
			}
			config.load(configFile);
			return true;
		}
		catch (Exception e) {
			Messenger.severe("Config did not load correctly, returned error:\n" + e.getMessage());
			return false;
		}
	}
	private void loaddefaults() {
		String[] players = {};
		config.addDefault("InvBackup.SaveIntervalMinutes", 5);
		config.addDefault("InvBackup.MaxNumberOfSaves", 30);
		config.addDefault("Players.list", Arrays.asList(players));
		config.options().copyDefaults(true);
		save();
	}
	public boolean save() {
		try {
			config.save(configFile);
		} catch (Exception e) { 
			Messenger.severe("Config failed to save, returned error:\n" + e.getMessage());
		}
		return true;
	}
	public int getint(String path) {
		return config.getInt(path);
	}
	public void setint(String path, int i) {
		config.set(path, i);
		save();
	}
	public void setstring(String path, String item) {
		config.set(path, item);
		save();
	}
	public List getlist(String path) {
		return config.getList(path);
	}
	public Boolean addtolist(String path, String item) {
		List<String> l = config.getStringList(path);
		if(!l.contains(item)) {
			l.add(item);
			config.set(path, l);
			save();
			return true;
		}else{
			return false;
		}
	}
}
