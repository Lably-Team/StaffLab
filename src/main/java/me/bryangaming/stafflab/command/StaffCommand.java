package me.bryangaming.stafflab.command;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.managers.SenderManager;
import me.bryangaming.stafflab.managers.StaffModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor {

    private PluginCore pluginCore;

    private final SenderManager senderManager;
    private final StaffModeManager staffModeManager;

    public StaffCommand(PluginCore pluginCore){
        this.pluginCore = pluginCore;
        this.senderManager = pluginCore.getManagers().getSenderManager();
        this.staffModeManager = pluginCore.getManagers().getStaffModeManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            senderManager.sendMessage(sender, "error.no-console");
            return false;
        }

        if (!senderManager.hasPermission(sender, "commands." + command.getName())){
            senderManager.sendMessage(sender, "error.no-perms");
            return false;
        }

        Player player = (Player) sender;

        if (!staffModeManager.isStaffModeEnabled(player)){
            staffModeManager.enableStaffMode(player);
        }else{
            staffModeManager.disableStaffMode(player);
        }
        return false;
    }
}
