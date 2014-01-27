package main.java.net.bigbadcraft.npcsquad.managers;

import java.util.Iterator;
import java.util.Random;

import main.java.net.bigbadcraft.npcsquad.NPCSquad;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class NPCSquadManager {
	
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
	}
	
	public void eliminateAllNPCs(String world) {
		NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
		
		npcRegistry.deregisterAll();
		Iterator<NPC> iterator = npcRegistry.iterator();
		
		while (iterator.hasNext()) {
			iterator.next().destroy();
			iterator.next().despawn();
			if (!iterator.hasNext()) {
				break;
			}
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
		
		int count = 1;
		while (iterator.hasNext()) {
			count++;
		}
		
		return count;
	}
	
	public int getTotalUntamedNPCs(String world) {
		
		Iterator<NPC> iterator = CitizensAPI.getNPCRegistry().iterator();
		
		int count = 1;
		
		while (iterator.hasNext()) {
			if (!iterator.next().isProtected()) {
				count++;
			}
		}
		
		return count;
	}
	
}
