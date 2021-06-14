package me.bryangaming.stafflab.listener;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.managers.VanishManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class TargetListener implements Listener {

    private final VanishManager vanishManager;

    public TargetListener(PluginCore pluginCore){
        this.vanishManager = pluginCore.getManagers().getVanishManager();
    }
    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event){

        if (!(event.getTarget() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getTarget();

        if (!vanishManager.isPlayerVanished(player)){
            return;
        }

        event.setCancelled(true);


    }
}
