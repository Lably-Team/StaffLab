package me.bryangaming.stafflab.loader;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.api.Loader;
import me.bryangaming.stafflab.listener.ChatListener;
import me.bryangaming.stafflab.listener.inventory.InventoryClickListener;
import me.bryangaming.stafflab.listener.QuitListener;
import me.bryangaming.stafflab.listener.TargetListener;
import me.bryangaming.stafflab.listener.inventory.InventoryCloseListener;
import me.bryangaming.stafflab.listener.inventory.InventoryInteractEntityListener;
import me.bryangaming.stafflab.listener.inventory.InventoryInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class EventLoader implements Loader {

    private final PluginCore pluginCore;

    public EventLoader(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {
        registerEvents(
                new QuitListener(pluginCore),
                new ChatListener(pluginCore),
                new TargetListener(pluginCore),
                new InventoryClickListener(pluginCore),
                new InventoryCloseListener(pluginCore),
                new InventoryInteractListener(pluginCore),
                new InventoryInteractEntityListener(pluginCore));
    }

    public void registerEvents(Listener... listeners){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        StaffLab staffLab = pluginCore.getPlugin();

        for (Listener listener : listeners){
            pluginManager.registerEvents(listener, staffLab);
        }
    }
}
