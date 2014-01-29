package main.java.net.bigbadcraft.npcsquad.managers;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import main.java.net.bigbadcraft.npcsquad.NPCSquad;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

public class NPCSquadManager {
	
	private final ChatColor G = ChatColor.GOLD;
	private final ChatColor W = ChatColor.WHITE;
	
	private NPCSquad plugin;
	public NPCSquadManager(NPCSquad plugin) {
		this.plugin = plugin;
	}
	
	public void distributeNPCs(String world, int x, int y, int z, boolean protectNPCs) {
		if (!plugin.botNames.isEmpty()) {
			NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
			
			Random random = new Random();
			int size = plugin.botNames.size();
			NPC npc = npcRegistry.createNPC(EntityType.PLAYER, plugin.botNames.get(random.nextInt(size)));
			
			npc.spawn(new Location(Bukkit.getWorld(world), x + 0.0D, y + 1.0D,z + 0.0D));
			npc.setProtected(protectNPCs);
			npc.data().setPersistent("health", (int) 20);
		}
	}
	
	public void chunkSpawnNPCs(String world, int x, int z, int botAmount, boolean protectNPCs) {
		if (!plugin.botNames.isEmpty()) {
			NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
			
			Random random = new Random();
			int size = plugin.botNames.size();
			NPC npc = npcRegistry.createNPC(EntityType.PLAYER, plugin.botNames.get(random.nextInt(size)));
			
			for (int i = 0; i < botAmount; i++) {
				npc.spawn(new Location(Bukkit.getWorld(world), x + 0.0D, 1.0D,z + 0.0D));
				npc.setProtected(protectNPCs);
				npc.data().setPersistent("health", (int) 20);
			}
		}
	}
	
	public void spawnNPCForPlayer(Player player, boolean protectNPC) {
		NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
		
		Random random = new Random();
		NPC npc = npcRegistry.createNPC(EntityType.PLAYER, plugin.botNames.get(random.nextInt(plugin.botNames.size())));
		
		@SuppressWarnings("deprecation")
		Block block = player.getTargetBlock(null, 30);
		Location loc;
		if (block != null) {
			loc = block.getLocation();
		} else {
			loc = player.getLocation();
		}
		
		npc.spawn(loc.add(0.0D, 1.0D, 0.0D));
		npc.setProtected(protectNPC);
		npc.data().setPersistent("health", (int) 20);
	}
	
	public void eliminateAllNPCs(String world) {
		
		/* Needs to be fixed, freezes server */
		
		List<NPC> npcList = Lists.newArrayList(CitizensAPI.getNPCRegistry().iterator());
		
		for (NPC npcs : npcList) {
			npcs.despawn();
			npcs.destroy();
		}
		
	}
	
	public boolean isNPC(Entity entity) {
		return CitizensAPI.getNPCRegistry().getNPC(entity) != null;
	}
	
	public NPC getNPC(Entity entity) {
		if (isNPC(entity)) {
			return CitizensAPI.getNPCRegistry().getNPC(entity);
		}
		return null;
	}
	
	public NPC getNPC(int id) {
		return CitizensAPI.getNPCRegistry().getById(id);
	}
	
	public int getTotalNPCs(String world) {
		
		Iterator<NPC> iterator = CitizensAPI.getNPCRegistry().iterator();
		
		int count = 0;
		while (iterator.hasNext()) {
			count++;
		}
		
		return count;
	}
	
	public double getNPCHealth(int id) {
		NPC npc = getNPC(id);
		return npc.data().get("health");
	}
	
	/**
	 * 
	 * @param player Send player information of the count of tamed bots
	 * the id of each bot, name and possibly health.
	 */
	public void sendTamedNPCInfoList(Player player) {
		int tamedBots = plugin.playerNPCs.get(player.getName()).size();
		
		player.sendMessage(G + "----------(" + W + "Tamed Bot(s) Info" + G + ")----------");
		player.sendMessage(G + "Total tamed bots: " + W + tamedBots);
		
		for (int ids : plugin.playerNPCs.get(player.getName())) {
			NPC npc = plugin.npcManager.getNPC(ids);
			String botName = npc.getFullName();
			double botHealth = getNPCHealth(ids);
			player.sendMessage(G + "Id: " + W + ids + ", " + G + "Name: " + W + botName + ", " + G + "Health: " + W + botHealth);
		}
		
		player.sendMessage(G + "-------------------------------------");
	}
	
	public int getTamedNPCs(Player player) {
		return plugin.playerNPCs.get(player.getName()).size();
	}
	
	public void killTamedNPCs(Player player) {
		
		List<Integer> npcIds = plugin.playerNPCs.get(player.getName());
		
		for (int ids : npcIds) {
			NPC npcs = getNPC(ids);
			npcs.despawn();
			npcs.destroy();
		}
		
	}
	
	public void dismissNPC(Player player, String npcName) {
		
		
	}
	
	public void appointNPC(Player player, String npcName) {
		
	}
	
	public int getTotalUntamedNPCs(String world) {
		
		Iterator<NPC> iterator = CitizensAPI.getNPCRegistry().iterator();
		
		int count = 0;
		
		while (iterator.hasNext()) {
			if (!iterator.next().isProtected()) {
				count++;
			}
		}
		
		return count;
	}
	
}
