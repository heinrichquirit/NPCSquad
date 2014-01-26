package main.java.net.bigbadcraft.npcsquad.listeners;

import java.util.ArrayList;

import main.java.net.bigbadcraft.npcsquad.NPCSquad;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NPCPlayerRegisterListener implements Listener {
	
	private NPCSquad plugin;
	public NPCPlayerRegisterListener(NPCSquad plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		String name = event.getPlayer().getName();
		if (!plugin.tamedNPCConf.contains(name)) {
			plugin.configHandler.reloadTamedNPCConf();
			plugin.tamedNPCConf.set(name, new ArrayList<NPC>());
			plugin.configHandler.saveTamedNPCConf();
		}
	}
}
