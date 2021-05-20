package me.bryangaming.stafflab;

import org.bukkit.plugin.java.JavaPlugin;

public class StaffLab extends JavaPlugin {

    @Override
    public void onEnable(){
        PluginCore pluginCore = new PluginCore(this);
        pluginCore.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
