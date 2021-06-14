package me.bryangaming.stafflab.listener.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryDropListener implements Listener {


    @EventHandler
    public void onDrop(PlayerDropItemEvent event){

        if (event.getPlayer().hasMetadata("staffguimode")){
            event.setCancelled(true);
            return;
        }
    }
}
