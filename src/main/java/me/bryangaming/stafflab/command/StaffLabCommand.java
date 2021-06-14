package me.bryangaming.stafflab.command;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.loader.DataLoader;
import me.bryangaming.stafflab.loader.file.FileLoader;
import me.bryangaming.stafflab.managers.SenderManager;
import me.bryangaming.stafflab.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StaffLabCommand implements CommandExecutor {

    private final PluginCore pluginCore;

    private final SenderManager senderManager;
    private final FileLoader files;

    public StaffLabCommand(PluginCore pluginCore) {
        this.pluginCore = pluginCore;

        this.files = pluginCore.getFiles();
        this.senderManager = pluginCore.getManagers().getSenderManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!senderManager.hasPermission(sender, "commands." + command.getName())){
            senderManager.sendMessage(sender, "error.no-perms");
            return false;
        }

        if (args.length < 1){
            senderManager.sendMessage(sender, "stafflab.help", true);
            return true;
        }

        switch (args[0]) {
            case "help":
                senderManager.sendMessage(sender, "stafflab.help", true);
                return true;
            case "reload":
                files.getConfigFile().reload();
                files.getMessagesFile().reload();
                DataLoader dataLoader = new DataLoader(pluginCore);
                dataLoader.load();
                senderManager.sendMessage(sender, "stafflab.reload");
                return true;
            default:
                senderManager.sendMessage(sender, "error.unknown-args", ReplaceableBuilder.create("%usage%",
                        TextUtils.createUsage(command.getName(), "help/reload")));
        }
        return true;
    }
}