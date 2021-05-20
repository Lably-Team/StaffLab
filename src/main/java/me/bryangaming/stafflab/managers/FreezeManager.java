package me.bryangaming.stafflab.managers;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.builder.InventoryBuilder;
import me.bryangaming.stafflab.loader.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FreezeManager {

    private InventoryBuilder inventorySaveBuilder;
    private final StaffLab staffLab;

    private final FileManager messagesFile;
    private final SenderManager senderManager;
    private int freezeTaskID;


    public FreezeManager(PluginCore pluginCore){
        this.staffLab = pluginCore.getPlugin();
        this.senderManager = pluginCore.getManagers().getSenderManager();

        this.messagesFile = pluginCore.getFiles().getMessagesFile();
    }

    public void freezePlayer(Player target){

        Inventory inventory = target.getInventory();
        EntityEquipment equipment = target.getEquipment();

        inventorySaveBuilder = InventoryBuilder.create()
                .setItems(inventory.getContents())
                .setHelmet(equipment.getHelmet())
                .setChestplate(equipment.getChestplate())
                .setLeggings(equipment.getLeggings())
                .setBoots(equipment.getBoots());

        inventory.clear();
        equipment.clear();

        target.setMetadata("freeze", new FixedMetadataValue(staffLab, true));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000000 , 100));
        target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000000 , 100));
        target.setGameMode(GameMode.ADVENTURE);
        target.setCanPickupItems(false);
        freezeTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(staffLab, new Runnable() {
            @Override
            public void run() {
                senderManager.sendMessage(target, "freeze.message");
            }
        },100,100);
    }

    public void unFreezePlayer(Player target){

        inventorySaveBuilder.givePlayer(target);

        target.removeMetadata("freeze", staffLab);
        target.removePotionEffect(PotionEffectType.SLOW);
        target.setGameMode(GameMode.ADVENTURE);
        target.setCanPickupItems(true);
        Bukkit.getScheduler().cancelTask(freezeTaskID);
        senderManager.sendMessage(target, "unfreeze.message");
    }

    public void punishFrozenPlayer(Player target){
        target.removeMetadata("freeze", staffLab);
        target.removePotionEffect(PotionEffectType.SLOW);
        target.setGameMode(GameMode.ADVENTURE);
        target.setCanPickupItems(true);
        Bukkit.getScheduler().cancelTask(freezeTaskID);
        for (String commands : messagesFile.getColoredStringList("freeze.command")){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands
                    .replace("%player%", target.getName()));
        }
    }
}
