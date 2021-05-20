package me.bryangaming.stafflab.command;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.managers.SenderManager;
import me.bryangaming.stafflab.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {

    private final SenderManager senderManager;

    public FreezeCommand(PluginCore pluginCore){
        this.senderManager = pluginCore.getSenderManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            senderManager.sendMessage(sender, "error.no-console");
            return false;
        }

        if (!senderManager.hasPermission(sender, command.getName())){
            senderManager.sendMessage(sender, "error.no-perms");
            return false;
        }

        if (args.length < 1){
            senderManager.sendMessage(sender, "error.no-args",
                    ReplaceableBuilder.create("%usage%", TextUtils.createUsage(command.getName(), "<player>")));
            return false;
        }
        
        Player target = Bukkit.getPlayer(args[1]);

        if (target == null){
            senderManager.sendMessage(sender, "error.no-online",
                    ReplaceableBuilder.create("%player%", args[1]);
            return false;
        }

        if (!senderManager.isFrozen(target)) {
            senderManager.freezePlayer(target);
            senderManager.sendMessage(sender, "freeze.target",
                    ReplaceableBuilder.create("%player%", target.getName()));
        }else{
            senderManager.unFreezePlayer(target);
            senderManager.sendMessage(sender, "unfreeze.target",
                    ReplaceableBuilder.create("%player%", target.getName()));
        }
        return false;
    }
}
