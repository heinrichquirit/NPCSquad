package main.java.net.bigbadcraft.npcsquad.commands;

import main.java.net.bigbadcraft.npcsquad.NPCSquad;
import main.java.net.bigbadcraft.npcsquad.managers.NPCSquadManager;
import main.java.net.bigbadcraft.npcsquad.utils.Permission;
import main.java.net.bigbadcraft.npcsquad.utils.Utils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCSquadCommand implements CommandExecutor {

	private final ChatColor G = ChatColor.GOLD;
	private final ChatColor W = ChatColor.WHITE;
	
	private NPCSquad plugin;
	private NPCSquadManager npcManager;
	public NPCSquadCommand(NPCSquad plugin) {
		this.plugin = plugin;
		npcManager = this.plugin.npcManager;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl,	String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must use this command in game!");
			return true;
		}
		
		Player player = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("npcsquad")) {
			processCommand(player, args);
		}
		
		return true;
	}
	
	private void processCommand(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(Utils.isPermitted(player, Permission.NPC_ADMIN) ? adminMenu() : helpMenu());
		}
		else if (args.length == 1) {
			if (Utils.isEqual(args[0], "killbots")) {
				
			}
			else if (Utils.isEqual(args[0], "listbots")) {
				
			}
			else if (Utils.isEqual(args[0], "untamedbots")) {
				
			}
			else if (Utils.isEqual(args[0], "spawnbot")) {
				if (Utils.isPermitted(player, Permission.NPC_ADMIN)) {
					npcManager.spawnNPCForPlayer(player);
				}
			}
			else if (Utils.isEqual(args[0], "killallbots")) {
				if (Utils.isPermitted(player, Permission.NPC_ADMIN)) {
					
				}
			}
		}
	}
	
	private String helpMenu() {
		return G + "----------(" + W + "NPCSquad" + G + ")----------\n"
				+ G + "Shortcut: " + W + "/nsquad or /ns\n"
				+ G + "-/npcsquad killbots" + W + " - Kills all of your tamed bots.\n"
				+ G + "-/npcsquad listbots" + W + " - List all your bots and health.\n"
				+ G + "-/npcsquad untamedbots" + W + " - List the amount of untamed bots in world.\n"
				+ G + "------------------------------";
	}
	
	private String adminMenu() {
		return G + "----------(" + W + "NPCSquad" + G + ")----------\n"
				+ G + "Shortcut: " + W + "/nsquad or /ns\n"
				+ G + "-/npcsquad killbots" + W + " - Kills all of your tamed bots.\n"
				+ G + "-/npcsquad listbots" + W + " - List all your bots and health.\n"
				+ G + "-/npcsquad untamedbots" + W + " - List the amount of untamed bots in world.\n"
				+ G + "-/npcsquad spawnbot" + W + " - Spawns an untamed bot.\n"
				+ G + "-/npcsquad killallbots" + W + " - Eliminates all bots in the world.\n"
				+ G + "------------------------------";
	}
	
}
