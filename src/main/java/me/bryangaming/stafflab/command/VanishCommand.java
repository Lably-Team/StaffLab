package me.bryangaming.stafflab.command;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.managers.SenderManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor{

    private StaffLab staffLab;
    private SenderManager senderManager;

    public VanishCommand(PluginCore pluginCore) {
        this.staffLab = pluginCore.getPlugin();
        this.senderManager = pluginCore.getManagers().getSenderManager();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            senderManager.sendMessage(sender, "error.no-console");
            return false;
        }

        if (!senderManager.hasPermission(sender, "commands." + command.getName())) {
            senderManager.sendMessage(sender, "error.no-perms");
            return false;
        }
        
        Player player = (Player) sender;
        if (!senderManager.isPlayerVanished(player)){
            senderManager.vanishPlayer(player);
        }else{
            senderManager.unVanishPlayer(player);
        }
        return false;
    }
}
