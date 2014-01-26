package main.java.net.bigbadcraft.npcsquad.managers;

import java.util.Random;

import main.java.net.bigbadcraft.npcsquad.NPCSquad;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class NPCSquadManager {
	
	private NPCSquad plugin;
	public NPCSquadManager(NPCSquad plugin) {
		this.plugin = plugin;
	}
	
	public void distributeNPCs(String world, int x, int y, int z) {
		if (plugin.botNames != null) {
			NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
			
			Random random = new Random();
			int size = plugin.botNames.size();
			NPC npc = npcRegistry.createNPC(EntityType.PLAYER, plugin.botNames.get(random.nextInt(size)));
			
			npc.spawn(new Location(Bukkit.getWorld(world), x + 0.0D, y + 1.0D,z + 0.0D));
		}
	}
	
	public void chunkSpawnNPCs(String world, int x, int z, int botAmount) {
		if (plugin.botNames != null) {
			NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
			
			Random random = new Random();
			int size = plugin.botNames.size();
			NPC npc = npcRegistry.createNPC(EntityType.PLAYER, plugin.botNames.get(random.nextInt(size)));
			
			for (int i = 0; i < botAmount; i++) {
				npc.spawn(new Location(Bukkit.getWorld(world), x + 0.0D, 1.0D,z + 0.0D));
			}
		}
	}
	
	public void spawnNPCForPlayer(Player player) {
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
	}
	
	public void eliminateAllNPCs(String world) {
		
	}
	
	public int getTotalNPCs(String world) {
		return 0;
	}
	
}
