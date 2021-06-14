package me.bryangaming.stafflab.managers;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.builder.InventoryBuilder;
import me.bryangaming.stafflab.data.ServerData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;

public class StaffModeManager {

    private InventoryBuilder inventorySaveBuilder;
    private GameMode playerGamemode;

    private final StaffLab staffLab;
    private final ServerData serverData;

    private final SenderManager senderManager;

    public StaffModeManager(PluginCore pluginCore){
        this.staffLab = pluginCore.getPlugin();

        this.serverData = pluginCore.getServerData();
        this.senderManager = pluginCore.getManagers().getSenderManager();
    }

    public void enableStaffMode(Player target){

        this.playerGamemode = target.getGameMode();
        target.setGameMode(GameMode.ADVENTURE);
        target.setCanPickupItems(false);

        target.setMetadata("staffguimode", new FixedMetadataValue(staffLab, "normal"));

        Inventory inventory = target.getInventory();
        EntityEquipment equipment = target.getEquipment();

        inventorySaveBuilder = InventoryBuilder.create()
                .setItems(Arrays.asList(inventory.getContents()))
                .setHelmet(equipment.getHelmet())
                .setChestplate(equipment.getChestplate())
                .setLeggings(equipment.getLeggings())
                .setBoots(equipment.getBoots());

        inventory.clear();
        equipment.clear();

        for (int data : serverData.getInventoryData().keySet()){
            inventory.setItem(data, serverData.getInventoryData().get(data).build());
        }
        senderManager.sendMessage(target, "staff.enabled");
    }

    public void disableStaffMode(Player target){

        target.setGameMode(playerGamemode);
        target.setCanPickupItems(true);
        target.removeMetadata("staffguimode", staffLab);

        inventorySaveBuilder.givePlayer(target);
        senderManager.sendMessage(target, "staff.disabled");
    }

    public void forzeDisableStaffMode(Player target){

        target.removeMetadata("staffguimode", staffLab);
        target.setGameMode(playerGamemode);
        target.setCanPickupItems(true);
        inventorySaveBuilder.givePlayer(target);
    }

    public boolean isStaffModeEnabled(Player target){
        return target.hasMetadata("staffguimode");
    }
}
