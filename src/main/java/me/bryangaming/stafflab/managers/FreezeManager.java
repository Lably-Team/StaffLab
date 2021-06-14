package me.bryangaming.stafflab.managers;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.builder.InventoryBuilder;
import me.bryangaming.stafflab.loader.file.FileManager;
import me.bryangaming.stafflab.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;

public class FreezeManager {

    private InventoryBuilder inventorySaveBuilder;
    private final StaffLab staffLab;

    private final FileManager configFile;
    private final FileManager messagesFile;
    private final SenderManager senderManager;
    private BukkitTask freezeTask;


    public FreezeManager(PluginCore pluginCore){
        this.staffLab = pluginCore.getPlugin();

        this.senderManager = pluginCore.getManagers().getSenderManager();

        this.configFile = pluginCore.getFiles().getConfigFile();
        this.messagesFile = pluginCore.getFiles().getMessagesFile();
    }

    public void freezePlayer(Player target){

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

        target.setMetadata("freeze", new FixedMetadataValue(staffLab, true));
        BukkitUtils.addUnlimitedEffects(target,
                PotionEffectType.JUMP,
                PotionEffectType.SLOW,
                PotionEffectType.BLINDNESS);
        target.setGameMode(GameMode.ADVENTURE);
        target.setCanPickupItems(false);
        senderManager.sendMessage(target, "freeze.target.message");
        freezeTask = Bukkit.getScheduler().runTaskTimer(staffLab, () -> {

                senderManager.sendMessage(target, "freeze.target.message");

        },100L, 100L);
    }

    public void unFreezePlayer(Player target){

        inventorySaveBuilder.givePlayer(target);

        target.removeMetadata("freeze", staffLab);

        BukkitUtils.removePotionEffects(target,
                PotionEffectType.JUMP,
                PotionEffectType.SLOW,
                PotionEffectType.BLINDNESS);

        target.setGameMode(GameMode.SURVIVAL);
        target.setCanPickupItems(true);
        freezeTask.cancel();
        senderManager.sendMessage(target, "unfreeze.message");
    }

    public void punishFrozenPlayer(Player target){

        target.removeMetadata("freeze", staffLab);

        BukkitUtils.removePotionEffects(target,
                PotionEffectType.JUMP,
                PotionEffectType.SLOW,
                PotionEffectType.BLINDNESS);

        target.setGameMode(GameMode.SURVIVAL);
        target.setCanPickupItems(true);
        freezeTask.cancel();

        for (Player player : Bukkit.getOnlinePlayers()){
            if (!player.hasPermission(configFile.getString("perms.watch.freeze"))){
                continue;
            }

            senderManager.sendMessage(player, "freeze.target.left");
        }

        for (String commands : messagesFile.getColoredStringList("freeze.target.command")){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands
                    .replace("%player%", target.getName()));
        }
    }
}
