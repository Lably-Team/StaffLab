package me.bryangaming.stafflab.managers;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class InvseeManager {

    private final StaffLab staffLab;

    public InvseeManager(PluginCore pluginCore){
        this.staffLab = pluginCore.getPlugin();
    }

    public void invseePlayer(Player sender, Player target){
        sender.setMetadata("invsee", new FixedMetadataValue(staffLab, true));
        sender.openInventory(target.getInventory());
    }

    public void removeInvseePlayer(Player sender){
      sender.removeMetadata("invsee", staffLab);
    }

    public boolean isSeenInventory(Player sender){
        return sender.hasMetadata("invsee");
    }
}
