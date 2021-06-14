package me.bryangaming.stafflab.listener.inventory;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.builder.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;

public class InventoryInteractListener implements Listener {
    private final Map<Integer, ItemBuilder> serverDataMap;

    public InventoryInteractListener(PluginCore pluginCore) {
        this.serverDataMap = pluginCore.getServerData().getInventoryData();
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event){
        if (event.getAction() != Action.RIGHT_CLICK_AIR){
            return;
        }

        if (!event.getPlayer().hasMetadata("staffguimode")){
            return;
        }

        Player player = event.getPlayer();

        event.setCancelled(true);

        String staffGuiMode = player.getMetadata("staffguimode").get(0).asString();

        int id = player.getInventory().getHeldItemSlot();

        if (staffGuiMode.equalsIgnoreCase("normal")){

            if (serverDataMap.get(id) == null) {
                return;
            }

            serverDataMap.get(id).callAction(event.getPlayer());
            return;
        }

    }
}
