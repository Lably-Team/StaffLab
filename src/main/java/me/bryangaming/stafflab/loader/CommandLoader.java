package me.bryangaming.stafflab.loader;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.api.Loader;
import me.bryangaming.stafflab.command.*;
import org.bukkit.Bukkit;

public class CommandLoader implements Loader {

    private PluginCore pluginCore;

    public CommandLoader(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {
        Bukkit.getPluginCommand("staff").setExecutor(new StaffCommand(pluginCore));
        Bukkit.getPluginCommand("stafflab").setExecutor(new StaffLabCommand(pluginCore));
        Bukkit.getPluginCommand("staffchat").setExecutor(new StaffChatCommand(pluginCore));
        Bukkit.getPluginCommand("freeze").setExecutor(new FreezeCommand(pluginCore));
        Bukkit.getPluginCommand("vanish").setExecutor(new VanishCommand(pluginCore));

    }
}
