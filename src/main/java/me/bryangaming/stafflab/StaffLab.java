package me.bryangaming.stafflab;

import org.bukkit.plugin.java.JavaPlugin;

public class StaffLab extends JavaPlugin {

    @Override
    public void onEnable(){
        PluginCore pluginCore = new PluginCore(this);
        pluginCore.init();
        getLogger().info("StaffLab by: " + getDescription().getAuthors().get(0));
        getLogger().info("If you want support, join in: https://discord.gg/wpSh4Bf4Es");
    }

    @Override
    public void onDisable() {
       getLogger().info("Thanks for using my plugin.");
    }
}
