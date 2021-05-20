package me.bryangaming.stafflab.command;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.managers.SenderManager;
import me.bryangaming.stafflab.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class StaffChatCommand implements CommandExecutor {

    private StaffLab staffLab;
    private final SenderManager senderManager;

    public StaffChatCommand(PluginCore pluginCore){
        this.staffLab = pluginCore.getPlugin();
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
        switch (args[0]) {
            case "-on":
                if (player.hasMetadata("staffchat")) {
                    senderManager.sendMessage(sender, "freeze.already.enabled");
                    return false;
                }
                player.setMetadata("staffchat", new FixedMetadataValue(staffLab, true));
                senderManager.sendMessage(sender, "freeze.mode.enabled");
                break;

            case "-off":
                if (!player.hasMetadata("staffchat")) {
                    senderManager.sendMessage(sender, "freeze.already.disabled");
                    return false;
                }
                player.removeMetadata("staffchat", staffLab);
                senderManager.sendMessage(sender, "freeze.mode.disabled");
                break;

            default:
                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                    if (senderManager.hasPermission(onlinePlayer, "receive.staffchat")) {
                        return;
                    }

                    senderManager.sendMessage(sender, "staffchat.format",
                            ReplaceableBuilder.create("%player%", player.getName()),
                            ReplaceableBuilder.create("%message%", String.join(" ", args)));
                });
        }
        return false;
    }
}
