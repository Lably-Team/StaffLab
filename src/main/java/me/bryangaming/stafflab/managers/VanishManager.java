package me.bryangaming.stafflab.managers;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.data.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class VanishManager {

    private final StaffLab staffLab;

    private final SenderManager senderManager;
    private final ServerData serverData;

    public VanishManager(PluginCore pluginCore) {
        this.staffLab = pluginCore.getPlugin();
        this.senderManager = pluginCore.getManagers().getSenderManager();
        this.serverData = pluginCore.getServerData();
    }

    public void vanishPlayer(Player target) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (senderManager.hasPermission(player, "watch.vanish")) {
                continue;
            }

            player.hidePlayer(staffLab, target);
        }

        target.setSilent(true);
        for (Entity entity : target.getNearbyEntities(10,10,10)){
            if (!(entity instanceof Creature)){
                return;
            }

            Creature creature = (Creature) entity;

            if (!creature.equals(target)){
                return;
            }

            creature.setTarget(null);
        }
        serverData.addVanishedPlayer(target);
    }

    public void unVanishPlayer(Player target){
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showPlayer(staffLab, target);
        }
        target.setSilent(false);
    }

    public boolean isPlayerVanished(Player target){
        return serverData.isPlayerVanished(target);
    }
}
