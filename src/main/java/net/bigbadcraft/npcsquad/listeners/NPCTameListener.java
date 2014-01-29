package main.java.net.bigbadcraft.npcsquad.listeners;

import java.util.ArrayList;

import main.java.net.bigbadcraft.npcsquad.NPCSquad;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NPCTameListener implements Listener {
	
	private NPCSquad plugin;
	private String PREFIX;
	public NPCTameListener(NPCSquad plugin) {
		this.plugin = plugin;
		PREFIX = plugin.messagePrefix;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onTame(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (plugin.npcManager.isNPC(event.getRightClicked())) {
			NPC npc = plugin.npcManager.getNPC(event.getRightClicked());
			if (player.getItemInHand().getType() == Material.valueOf(plugin.tameItem.toUpperCase())) {
				if (!npc.isProtected()) {
					if (!plugin.playerNPCs.containsKey(player.getName())) {
						ArrayList<Integer> npcs = new ArrayList<Integer>();
						npcs.add(npc.getId());
						npc.setProtected(true);
						npc.getNavigator().setTarget(player.getLocation());
						plugin.playerNPCs.put(player.getName(), npcs);
						plugin.saveHashMap();
						player.sendMessage(PREFIX + " Successfully tamed bot.");
					} else {
						ArrayList<Integer> npcs = plugin.playerNPCs.get(player.getName());
						npcs.add(npc.getId());
						npc.setProtected(true);
						// may use playermoveevent
						// get all players npc and use this method to update its location
						npc.getNavigator().setTarget(player.getLocation());
						plugin.playerNPCs.put(player.getName(), npcs);
						plugin.saveHashMap();
						player.sendMessage(PREFIX + " Successfully tamed bot.");
					}
				} else {
					player.sendMessage(PREFIX + " That bot is already tamed.");
				}
			} else {
				player.sendMessage(PREFIX + " Incorrect tame item, use: " + plugin.tameItem);
			}
		}
		
	}
}
