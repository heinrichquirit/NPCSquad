package main.java.net.bigbadcraft.npcsquad.listeners;

import main.java.net.bigbadcraft.npcsquad.NPCSquad;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NPCDamageListener implements Listener {
	
	private NPCSquad plugin;
	public NPCDamageListener(NPCSquad plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onNPCDamage(EntityDamageEvent event) {
		if (plugin.npcManager.isNPC(event.getEntity())) {
			int damage = 1;
			NPC npc = plugin.npcManager.getNPC(event.getEntity());
			int npcHealth = npc.data().get("health");
			int damagedHealth = npcHealth % 1 == 1 ? 0 : npcHealth - damage;
			event.setCancelled(true);
			plugin.npcManager.getNPC(event.getEntity()).data().setPersistent("health", damagedHealth);
			plugin.saveHashMap();
		}
	}
	
}
