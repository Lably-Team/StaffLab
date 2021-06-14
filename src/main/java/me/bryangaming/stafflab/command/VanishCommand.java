package me.bryangaming.stafflab.command;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.managers.SenderManager;
import me.bryangaming.stafflab.managers.VanishManager;
import me.bryangaming.stafflab.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor{

    private StaffLab staffLab;

    private final SenderManager senderManager;
    private final VanishManager vanishManager;

    public VanishCommand(PluginCore pluginCore) {
        this.staffLab = pluginCore.getPlugin();

        this.senderManager = pluginCore.getManagers().getSenderManager();
        this.vanishManager = pluginCore.getManagers().getVanishManager();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            senderManager.sendMessage(sender, "error.no-console");
            return true;
        }

        if (!senderManager.hasPermission(sender, "commands." + command.getName())) {
            senderManager.sendMessage(sender, "error.no-perms");
            return true;
        }

        if (args.length < 1) {
            Player player = (Player) sender;

            if (!vanishManager.isPlayerVanished(player)) {
                vanishManager.vanishPlayer(player);
                senderManager.sendMessage(sender, "vanish.player.enabled");
            } else {
                vanishManager.unVanishPlayer(player);
                senderManager.sendMessage(sender, "vanish.player.disabled");
            }
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null){
            senderManager.sendMessage(sender, "error.no-online",
                    ReplaceableBuilder.create("%player%", args[1]));
            return true;
        }

        if (!vanishManager.isPlayerVanished(target)) {
            vanishManager.vanishPlayer(target);
            senderManager.sendMessage(sender, "vanish.player.enabled",
                        ReplaceableBuilder.create("%player%", target.getName()));
        } else {
            vanishManager.unVanishPlayer(target);
            senderManager.sendMessage(sender, "vanish.target.disabled",
                    ReplaceableBuilder.create("%player%", target.getName()));
        }
        return true;
    }
}
