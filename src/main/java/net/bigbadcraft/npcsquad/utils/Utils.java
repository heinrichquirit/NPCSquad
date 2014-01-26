package main.java.net.bigbadcraft.npcsquad.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
	
	public static void loadFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void log(Level lvl, String message) {
		Bukkit.getLogger().log(lvl, message);
	}
	
	public static boolean isPermitted(Player player, String permission) {
		return player.isOp() || player.hasPermission("*") || player.hasPermission(permission);
	}
	
	public static boolean isEqual(String first, String last) {
		return first.equalsIgnoreCase(last);
	}
	
	public static String parseColors(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
}
