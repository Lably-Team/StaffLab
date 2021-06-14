package me.bryangaming.stafflab.listener.inventory;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.builder.GuiBuilder;
import me.bryangaming.stafflab.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

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

        Player player = Bukkit.getPlayerExact(event.getWhoClicked().getName());

        String staffguiMode = player.getMetadata("staffguimode").get(0).asString();

        int id = player.getInventory().getHeldItemSlot();

        GuiBuilder guiBuilder = staffGuiModeListMap.get(staffguiMode);

        if (guiBuilder.getItem(id) == null) {
            return;
        }

        guiBuilder.getItem(id).callAction(event.getWhoClicked());

    }
}
