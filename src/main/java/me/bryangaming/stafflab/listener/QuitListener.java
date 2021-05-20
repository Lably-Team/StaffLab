package me.bryangaming.stafflab.listener;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.managers.SenderManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final SenderManager senderManager;

    public QuitListener(PluginCore pluginCore) {
        this.senderManager = pluginCore.getSenderManager();

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        Player player = event.getPlayer();

        if (senderManager.isStaffModeEnabled(player)){
            senderManager.forzeDisableStaffMode(player);
        }

        if (senderManager.isFrozen(player)){
            senderManager.punishFrozenPlayer(player);
        }
    }
}
