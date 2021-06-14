package me.bryangaming.stafflab.listener.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryDragListener implements Listener {

    @EventHandler
    public void onChat(InventoryDragEvent event){
        if (event.getWhoClicked().hasMetadata("staffguimode")){
            event.setCancelled(true);
            return;
        }
    }
}
