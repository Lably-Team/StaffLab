package me.bryangaming.stafflab.listener.inventory;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.managers.InvseeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class InventoryCloseListener implements Listener {


    private final StaffLab staffLab;
    private final InvseeManager invseeManager;

    public InventoryCloseListener(PluginCore pluginCore) {
        this.staffLab = pluginCore.getPlugin();
        this.invseeManager = pluginCore.getManagers().getInvseeManager();
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = Bukkit.getPlayer(event.getPlayer().getName());

        if (invseeManager.isSeenInventory(player)){
            invseeManager.disableInventoryMode(player);
        }

        if (!player.hasMetadata("staffguimode")){
            return;
        }

        if (!(player.getMetadata("staffguimode").get(0).asString().equalsIgnoreCase("normal"))){
            player.setMetadata("staffguimode", new FixedMetadataValue(staffLab, "normal"));
            return;
        }

    }
}
