package me.bryangaming.stafflab.listener.inventory;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.builder.InteractBuilder;
import me.bryangaming.stafflab.builder.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.Map;

public class InventoryInteractEntityListener implements Listener {

    private final StaffLab staffLab;
    private final Map<Integer, ItemBuilder> serverDataMap;

    public InventoryInteractEntityListener(PluginCore pluginCore) {
        this.staffLab = pluginCore.getPlugin();
        this.serverDataMap = pluginCore.getServerData().getInventoryData();
    }


    @EventHandler
    public void onInteractEvent(PlayerInteractAtEntityEvent event) {

        if (!event.getPlayer().hasMetadata("staffguimode")) {
            return;
        }
        Player player = event.getPlayer();

        event.setCancelled(true);

        String staffGuiMode = player.getMetadata("staffguimode").get(0).asString();

        int id = player.getInventory().getHeldItemSlot();

        if (staffGuiMode.equalsIgnoreCase("normal")) {
            if (serverDataMap.get(id) == null) {
                return;
            }

            serverDataMap.get(id).callEntityAction(InteractBuilder.create(player, event.getRightClicked()));
            return;
        }
    }
}
