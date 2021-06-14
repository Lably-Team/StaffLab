package me.bryangaming.stafflab.listener;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.managers.SenderManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.stream.Collectors;

public class ChatListener implements Listener {

    private final SenderManager senderManager;

    public ChatListener(PluginCore pluginCore){
        this.senderManager = pluginCore.getManagers().getSenderManager();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){

        if (event.getPlayer().hasMetadata("freeze")){
            event.setCancelled(true);
            return;
        }

        if (event.getPlayer().hasMetadata("staffchat")) {
            event.setCancelled(true);
            List<Player> playerList = Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(onlinePlayer -> senderManager.hasPermission(onlinePlayer, "watch.staffchat"))
                    .collect(Collectors.toList());

            playerList.forEach(onlinePlayer -> {
                if (senderManager.hasPermission(onlinePlayer, "watch.staffchat")) {
                    return;
                }

                senderManager.sendMessage(onlinePlayer, "staffchat.format",
                        ReplaceableBuilder.create("%player%", event.getPlayer().getName()),
                        ReplaceableBuilder.create("%message%", String.join(" ", event.getMessage())));
            });
        }
    }
}
