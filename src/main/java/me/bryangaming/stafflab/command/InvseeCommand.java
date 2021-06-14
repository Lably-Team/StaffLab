package me.bryangaming.stafflab.command;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.managers.InvseeManager;
import me.bryangaming.stafflab.managers.SenderManager;
import me.bryangaming.stafflab.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {

    private StaffLab staffLab;
    private final SenderManager senderManager;
    private final InvseeManager invseeManager;

    public InvseeCommand(PluginCore pluginCore){
        this.staffLab = pluginCore.getPlugin();
        this.senderManager = pluginCore.getManagers().getSenderManager();
        this.invseeManager = pluginCore.getManagers().getInvseeManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            senderManager.sendMessage(sender, "error.no-console");
            return false;
        }

        Player player = (Player) sender;
        if (!senderManager.hasPermission(sender, "commands." + command.getName())) {
            senderManager.sendMessage(sender, "error.no-perms");
            return false;
        }

        if (args.length < 1) {
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

        invseeManager.seePlayerInventory(player, target);
        return false;
    }
}
