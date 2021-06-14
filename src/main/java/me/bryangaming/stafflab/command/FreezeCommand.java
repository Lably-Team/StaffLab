package me.bryangaming.stafflab.command;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.managers.FreezeManager;
import me.bryangaming.stafflab.managers.SenderManager;
import me.bryangaming.stafflab.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {

    private final SenderManager senderManager;
    private final FreezeManager freezeManager;

    public FreezeCommand(PluginCore pluginCore){
        this.senderManager = pluginCore.getManagers().getSenderManager();
        this.freezeManager = pluginCore.getManagers().getFreezeManager();
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
                    ReplaceableBuilder.create("%usage%", TextUtils.createUsage(command.getName(), "<player>")));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null){
            senderManager.sendMessage(sender, "error.no-online",
                    ReplaceableBuilder.create("%player%", args[0]));
            return false;
        }

        if (!target.hasMetadata("freeze")) {
            freezeManager.freezePlayer(target);
            senderManager.sendMessage(sender, "freeze.sender.message",
                    ReplaceableBuilder.create("%player%", target.getName()));
        }else{
            freezeManager.unFreezePlayer(target);
            senderManager.sendMessage(sender, "unfreeze.sender.message",
                    ReplaceableBuilder.create("%player%", target.getName()));
        }
        return false;
    }
}
