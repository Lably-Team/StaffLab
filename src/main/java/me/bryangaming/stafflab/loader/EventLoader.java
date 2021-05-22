package me.bryangaming.stafflab.loader;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.api.Loader;
import me.bryangaming.stafflab.listener.ChatListener;
import me.bryangaming.stafflab.listener.ClickListener;
import me.bryangaming.stafflab.listener.TargetListener;
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
                new ClickListener(pluginCore),
                new ClickListener(pluginCore),
                new ChatListener(pluginCore),
                new TargetListener(pluginCore));
    }

    public void registerEvents(Listener... listeners){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        StaffLab staffLab = pluginCore.getPlugin();

        for (Listener listener : listeners){
            pluginManager.registerEvents(listener, staffLab);
        }
    }
}
