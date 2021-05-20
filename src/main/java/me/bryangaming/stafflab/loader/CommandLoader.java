package me.bryangaming.stafflab.loader;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.api.Loader;
import me.bryangaming.stafflab.command.FreezeCommand;
import me.bryangaming.stafflab.command.StaffChatCommand;
import me.bryangaming.stafflab.command.StaffCommand;
import me.bryangaming.stafflab.command.StaffLabCommand;
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

    }
}
