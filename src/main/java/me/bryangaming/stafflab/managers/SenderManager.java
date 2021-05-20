package me.bryangaming.stafflab.managers;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.builder.InventoryBuilder;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.data.ServerData;
import me.bryangaming.stafflab.loader.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SenderManager {

    private final ServerData serverData;
    private final StaffLab staffLab;

    private final FileManager configFile;
    private final FileManager messagesFile;

    private InventoryBuilder inventorySaveBuilder;
    private int freezeTaskID;


    public SenderManager(PluginCore pluginCore){
        this.staffLab = pluginCore.getPlugin();

        this.configFile = pluginCore.getFiles().getConfigFile();
        this.messagesFile = pluginCore.getFiles().getMessagesFile();

        this.serverData = pluginCore.getServerData();
    }

    public void sendMessage(CommandSender sender, String path){
        sender.sendMessage(messagesFile.getColoredString(path));
    }

    public void sendMessage(CommandSender sender, String paths, boolean isList){
        if (!isList){
            return;
        }

        for (String path : messagesFile.getColoredStringList(paths)) {
            sender.sendMessage(path);
        }
    }

    public void sendMessage(CommandSender sender, String path, ReplaceableBuilder... replaceableBuilders){

        for (ReplaceableBuilder replaceableBuilder : replaceableBuilders){
            path = path.replace(replaceableBuilder.getTarget(), replaceableBuilder.getReplacement());
        }

        sender.sendMessage(messagesFile.getColoredString(path));
    }

    public boolean hasPermission(CommandSender sender, String path){
        String permsPath = configFile.getString("perms." + path);

        if (permsPath == null){
            return true;
        }

        if (permsPath.equalsIgnoreCase("none")){
            return true;
        }

        return sender.hasPermission(permsPath);
    }

    public void vanishPlayer(Player target) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (hasPermission(player, "watch.vanish")) {
                continue;
            }

            player.hidePlayer(staffLab, target);
        }
        serverData.addVanishedPlayer(target);
    }

    public void unVanishPlayer(Player target){
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showPlayer(staffLab, target);
        }
    }

    public boolean isPlayerVanished(Player target){
        return serverData.isPlayerVanished(target);
    }


}
