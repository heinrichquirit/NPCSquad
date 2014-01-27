package main.java.net.bigbadcraft.npcsquad.listeners;

import java.util.Random;
import java.util.logging.Level;

import main.java.net.bigbadcraft.npcsquad.NPCSquad;
import main.java.net.bigbadcraft.npcsquad.utils.Utils;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class NPCChunkLoadListener implements Listener {
	
	private NPCSquad plugin;
	public NPCChunkLoadListener(NPCSquad plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onLoad(ChunkLoadEvent event) {
		String world = event.getWorld().getName();
		Chunk chunk = event.getChunk();
		
		if (event.isNewChunk()) {
			if (plugin.npcManager.getTotalNPCs(world) < plugin.botLimit) {
				int remainder = plugin.npcManager.getTotalNPCs(world) % plugin.botLimit;
				
				Random random = new Random();
				
				int x = random.nextInt(chunk.getX());
				int z = random.nextInt(chunk.getZ());
				
				plugin.npcManager.chunkSpawnNPCs(world, x, z, remainder, false);
				Utils.log(Level.INFO, "Spawned " + remainder + " bots in world: " + world); 
			}
		}
		
	}
}
