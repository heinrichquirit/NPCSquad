package main.java.net.bigbadcraft.npcsquad.listeners;

import main.java.net.bigbadcraft.npcsquad.NPCSquad;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NPCFollowPlayerListener implements Listener {
	
	private NPCSquad plugin;
	public NPCFollowPlayerListener(NPCSquad plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onNPCFollow(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (player.getLocation() != null) {
			for (int ids : plugin.playerNPCs.get(player.getName())) {
				NPC npc = plugin.npcManager.getNPC(ids);
				npc.getNavigator().setTarget(player.getLocation());
			}
		}
	}
}
