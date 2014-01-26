package main.java.net.bigbadcraft.npcsquad.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import main.java.net.bigbadcraft.npcsquad.NPCSquad;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler {
	
	private NPCSquad plugin;
	public ConfigHandler(NPCSquad plugin) {
		this.plugin = plugin;
	}
	
	public void reloadTamedNPCConf() {
	    if (plugin.tamedNPCConf == null) {
	    	plugin.tamedNPCFile = new File(plugin.getDataFolder(), "tamednpcs.yml");
	    }
	    
	    plugin.tamedNPCConf = YamlConfiguration.loadConfiguration(plugin.tamedNPCFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource("tamednpcs.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        plugin.tamedNPCConf.setDefaults(defConfig);
	    }
	}
	
	public void saveTamedNPCConf() {
	    if (plugin.tamedNPCConf == null || plugin.tamedNPCFile == null) {
	        return;
	    }
	    try {
	        plugin.tamedNPCConf.save(plugin.tamedNPCFile);
	    } catch (IOException ex) {
	       	Utils.log(Level.SEVERE, "Could not save config to " + plugin.tamedNPCFile);
	       	ex.printStackTrace();
	    }
	}
	
}
