package me.bryangaming.stafflab.command;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.managers.SenderManager;
import me.bryangaming.stafflab.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor {

    private PluginCore pluginCore;
    private SenderManager senderManager;

    public StaffCommand(PluginCore pluginCore){
        this.pluginCore = pluginCore;
        this.senderManager = pluginCore.getSenderManager();
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

        if (args.length < 1){
            senderManager.sendMessage(sender, "error.no-args",
                    ReplaceableBuilder.create("%usage%", TextUtils.createUsage(command.getName(), args)));
            return false;
        }

        Player player = (Player) sender;

        if (!senderManager.isStaffModeEnabled(player)){
            senderManager.enableStaffMode(player);
        }else{
            senderManager.disableStaffMode(player);
        }
        return false;
    }
}
