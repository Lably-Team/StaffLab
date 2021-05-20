package me.bryangaming.stafflab.command;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.loader.file.FileLoader;
import me.bryangaming.stafflab.managers.SenderManager;
import me.bryangaming.stafflab.module.DataModule;
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
        this.senderManager = pluginCore.getSenderManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!senderManager.hasPermission(sender, command.getName())){
            senderManager.sendMessage(sender, "error.no-perms");
            return false;
        }

        files.getConfigFile().reload();
        files.getMessagesFile().reload();

        DataModule dataModule = new DataModule(pluginCore);
        dataModule.load();
        senderManager.sendMessage(sender, "stafflab.message", true);
        return false;
    }
}