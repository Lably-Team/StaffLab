package me.bryangaming.stafflab.listener;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.builder.GuiBuilder;
import me.bryangaming.stafflab.builder.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class ClickListener implements Listener {

    private final Map<Integer, ItemBuilder> serverDataMap;
    private final Map<String, GuiBuilder> staffGuiModeListMap;

    public ClickListener(PluginCore pluginCore){
        this.serverDataMap = pluginCore.getServerData().getInventoryData();
        this.staffGuiModeListMap = pluginCore.getServerData().getData();
    }

    @EventHandler
    public void onClickEvent(InventoryClickEvent event){
        if (!event.getWhoClicked().hasMetadata("staffmode")){
            return;
        }

        if (event.getClickedInventory() == null){
            return;
        }

        int id = event.getSlot();
        event.setCancelled(true);

        String staffguiMode = event.getWhoClicked().getMetadata("staffguimode").get(0).asString();

        if (staffguiMode.equalsIgnoreCase("normal")){
            if (serverDataMap.get(id) == null) {
                return;
            }

            serverDataMap.get(id).callAction(event);
            return;
        }

        GuiBuilder guiBuilder = staffGuiModeListMap.get(staffguiMode);

        if (guiBuilder.getItem(id) != null){
            return;
        }

        guiBuilder.getItem(id).callAction(event);


    }
}
