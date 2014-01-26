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
		
		if (event.getRightClicked() instanceof Player) {
			player.sendMessage("An npc.");
			NPC npc = (NPC) event.getRightClicked();
			if (player.getItemInHand().getType() == Material.valueOf(plugin.tameItem.toUpperCase())) {
				if (!npc.isProtected()) {
					if (!plugin.playerNPCs.containsKey(player.getName())) {
						ArrayList<NPC> npcs = new ArrayList<NPC>();
						npcs.add(npc);
						npc.setProtected(true);
						plugin.playerNPCs.put(player.getName(), npcs);
						plugin.saveHashMap();
						player.sendMessage(PREFIX + " Successfully tamed bot.");
					} else {
						ArrayList<NPC> npcs = plugin.playerNPCs.get(player.getName());
						npcs.add(npc);
						npc.setProtected(true);
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
		} else {
			player.sendMessage("Not an NPC.");
		}
		
	}
}
