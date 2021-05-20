package me.bryangaming.stafflab.managers;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.builder.InventoryBuilder;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.data.ServerData;
import me.bryangaming.stafflab.loader.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SenderManager {

    private final ServerData serverData;
    private final StaffLab staffLab;

    private final FileManager configFile;
    private final FileManager messagesFile;

    private InventoryBuilder inventoryBuilder;
    private int freezeTaskID;

    public SenderManager(PluginCore pluginCore){
        this.staffLab = pluginCore.getPlugin();

        this.configFile = pluginCore.getFiles().getConfigFile();
        this.messagesFile = pluginCore.getFiles().getMessagesFile();

        this.serverData = pluginCore.getServerData();
    }

    public void sendMessage(CommandSender sender, String path){
        sender.sendMessage(messagesFile.getColoredString(path));
    }

    public void sendMessage(CommandSender sender, String paths, boolean isList){
        if (!isList){
            return;
        }

        for (String path : messagesFile.getColoredStringList(paths)) {
            sender.sendMessage(path);
        }
    }

    public void sendMessage(CommandSender sender, String path, ReplaceableBuilder... replaceableBuilders){

        for (ReplaceableBuilder replaceableBuilder : replaceableBuilders){
            path = path.replace(replaceableBuilder.getTarget(), replaceableBuilder.getReplacement());
        }

        sender.sendMessage(messagesFile.getColoredString(path));
    }

    public boolean hasPermission(CommandSender sender, String path){
        String permsPath = configFile.getString("perms." + path);

        if (permsPath == null){
            return true;
        }

        if (permsPath.equalsIgnoreCase("none")){
            return true;
        }

        return sender.hasPermission(permsPath);
    }

    public boolean isFrozen(Player player){
        return player.hasMetadata("freeze");
    }

    public void freezePlayer(Player target){
        target.setMetadata("freeze", new FixedMetadataValue(staffLab, true));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000000 , 100));
        target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000000 , 100));
        target.setGameMode(GameMode.ADVENTURE);
        target.setCanPickupItems(false);
        freezeTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(staffLab, new Runnable() {
            @Override
            public void run() {
                sendMessage(target, "freeze.message");
            }
        },20,20);
    }

    public void unFreezePlayer(Player target){
        target.removeMetadata("freeze", staffLab);
        target.removePotionEffect(PotionEffectType.SLOW);
        target.setGameMode(GameMode.ADVENTURE);
        target.setCanPickupItems(true);
        Bukkit.getScheduler().cancelTask(freezeTaskID);
        sendMessage(target, "unfreeze.message");
    }

    public void punishFrozenPlayer(Player target){
        target.removeMetadata("freeze", staffLab);
        target.removePotionEffect(PotionEffectType.SLOW);
        target.setGameMode(GameMode.ADVENTURE);
        target.setCanPickupItems(true);
        Bukkit.getScheduler().cancelTask(freezeTaskID);
        for (String commands : configFile.getColoredStringList("freeze.command")){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands
                    .replace("%player%", target.getName()));
        }
    }


    public void enableStaffMode(Player target){

        target.setMetadata("staffmode", new FixedMetadataValue(staffLab, true));

        Inventory inventory = target.getInventory();
        EntityEquipment equipment = target.getEquipment();

        inventory.clear();
        equipment.clear();

        inventoryBuilder = InventoryBuilder.create()
                .setItems(inventory.getContents())
                .setHelmet(equipment.getHelmet())
                .setChestplate(equipment.getChestplate())
                .setLeggings(equipment.getLeggings())
                .setBoots(equipment.getBoots());

        for (int data : serverData.getInventoryData().keySet()){
            inventory.setItem(data, serverData.getInventoryData().get(data).build());
        }
        sendMessage(target, "staff.enabled");
    }

    public void disableStaffMode(Player target){

        target.removeMetadata("staffmode", staffLab);
        inventoryBuilder.givePlayer(target);
        sendMessage(target, "staff.disabled");
    }

    public void forzeDisableStaffMode(Player target){

        target.removeMetadata("staffmode", staffLab);
        inventoryBuilder.givePlayer(target);
    }

    public boolean isStaffModeEnabled(Player target){
        return target.hasMetadata("staffmode");
    }

}
