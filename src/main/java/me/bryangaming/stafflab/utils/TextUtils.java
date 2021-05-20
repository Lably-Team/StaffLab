package me.bryangaming.stafflab.utils;

import org.bukkit.ChatColor;

public class TextUtils {

    public static String colorize(String path) {
        return ChatColor.translateAlternateColorCodes('&', path);
    }

    public static String createUsage(String command, String... args){
        return "/" + command + " " +  String.join(" ", args);
    }
}
