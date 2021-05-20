package me.bryangaming.stafflab.listener;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.managers.FreezeManager;
import me.bryangaming.stafflab.managers.StaffModeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final FreezeManager freezeModeManager;
    private final StaffModeManager staffModeManager;

    public QuitListener(PluginCore pluginCore) {
        this.freezeModeManager = pluginCore.getManagers().getFreezeManager();
        this.staffModeManager = pluginCore.getManagers().getStaffModeManager();

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        Player player = event.getPlayer();

        if (staffModeManager.isStaffModeEnabled(player)){
            staffModeManager.forzeDisableStaffMode(player);
        }

        if (player.hasMetadata("freeze")){
            freezeModeManager.punishFrozenPlayer(player);
        }
    }
}
