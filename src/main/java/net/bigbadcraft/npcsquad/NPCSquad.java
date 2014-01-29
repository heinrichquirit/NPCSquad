package main.java.net.bigbadcraft.npcsquad;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import main.java.net.bigbadcraft.npcsquad.commands.NPCSquadCommand;
import main.java.net.bigbadcraft.npcsquad.listeners.NPCDamageListener;
import main.java.net.bigbadcraft.npcsquad.listeners.NPCFollowPlayerListener;
import main.java.net.bigbadcraft.npcsquad.listeners.NPCPlayerRegisterListener;
import main.java.net.bigbadcraft.npcsquad.listeners.NPCTameListener;
import main.java.net.bigbadcraft.npcsquad.managers.NPCSquadManager;
import main.java.net.bigbadcraft.npcsquad.utils.ConfigHandler;
import main.java.net.bigbadcraft.npcsquad.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class NPCSquad extends JavaPlugin {
	
	public HashMap<String, ArrayList<Integer>> playerNPCs = new HashMap<String, ArrayList<Integer>>();
	
	public List<String> botNames;
	public List<String> worlds;
	
	public String tameItem;
	public String messagePrefix;
	public String permissionMessage;
	public int botTameAttempts;
	public int despawnTime;
	public int botLimit;
	
	public NPCSquadManager npcManager;
	
	public FileConfiguration tamedNPCConf;
	public File tamedNPCFile;
	
	public ConfigHandler configHandler;
	
	public void onEnable() {
		
		if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
			Utils.log(Level.INFO, "[NPCSquad] Hooked into Citizens!");
		}
		
		saveDefaultConfig();
		loadValues();
		
		npcManager = new NPCSquadManager(this);
		
		configHandler = new ConfigHandler(this);
		
		tamedNPCFile = new File(getDataFolder(), "tamednpcs.yml");
		Utils.loadFile(tamedNPCFile);
		tamedNPCConf = YamlConfiguration.loadConfiguration(tamedNPCFile);
		configHandler.reloadTamedNPCConf();
		
		loadHashMap();
		
		getServer().getPluginManager().registerEvents(new NPCPlayerRegisterListener(this), this);
		getServer().getPluginManager().registerEvents(new NPCTameListener(this), this);
		getServer().getPluginManager().registerEvents(new NPCDamageListener(this), this);
		getServer().getPluginManager().registerEvents(new NPCFollowPlayerListener(this), this);
		
		getCommand("npcsquad").setExecutor(new NPCSquadCommand(this));
	}
	
	public void onDisable() {
		
		saveHashMap();
		
	}
	
	private void loadValues() {
		tameItem = getConfig().getString("tameItem");
		messagePrefix = Utils.parseColors(getConfig().getString("messagePrefix")) + ChatColor.WHITE;
		permissionMessage = Utils.parseColors(getConfig().getString("permissionMessage"));
		botTameAttempts = getConfig().getInt("botTameAttempts");
		despawnTime = getConfig().getInt("despawnTime");
		botLimit = getConfig().getInt("botLimit");
		botNames = getConfig().getStringList("botNames");
		worlds = getConfig().getStringList("worlds");
	}
	
	@SuppressWarnings("unchecked")
	private void loadHashMap() {
		if (tamedNPCConf.getKeys(true) != null) {
			for (String names : tamedNPCConf.getKeys(true)) {
				playerNPCs.put(names, (ArrayList<Integer>)tamedNPCConf.getList(names));
			}
		}
	}
	
	public void saveHashMap() {
		if (playerNPCs != null) {
			configHandler.reloadTamedNPCConf();
			for (Entry<String, ArrayList<Integer>> entry : playerNPCs.entrySet()) {
				tamedNPCConf.set(entry.getKey(), entry.getValue());
			}
			configHandler.saveTamedNPCConf();
		}
	}
}
