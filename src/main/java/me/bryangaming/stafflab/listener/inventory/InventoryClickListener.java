package me.bryangaming.stafflab.listener.inventory;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.builder.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class InventoryClickListener implements Listener {


    private final Map<String, GuiBuilder> staffGuiModeListMap;

    public InventoryClickListener(PluginCore pluginCore) {
        this.staffGuiModeListMap = pluginCore.getServerData().getData();
    }

    @EventHandler()
    public void onCLickEvent(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (!event.getCurrentItem().hasItemMeta()) {
            return;
        }

        if (!event.getWhoClicked().hasMetadata("staffguimode")){
            return;
        }

        event.setCancelled(true);
        Player player = Bukkit.getPlayer(event.getWhoClicked().getUniqueId());
        String staffguiMode = player.getMetadata("staffguimode").get(0).asString();

        if (staffguiMode.equalsIgnoreCase("normal")){
            return;
        }

        int id = player.getInventory().getHeldItemSlot();

        GuiBuilder guiBuilder = staffGuiModeListMap.get(staffguiMode);

        if (guiBuilder.getItem(id) == null) {
            return;
        }

        guiBuilder.getItem(id).callAction(event.getWhoClicked());

    }
}
