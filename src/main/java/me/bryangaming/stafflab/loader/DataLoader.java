package me.bryangaming.stafflab.loader;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.api.Loader;
import me.bryangaming.stafflab.builder.GuiBuilder;
import me.bryangaming.stafflab.builder.ItemBuilder;
import me.bryangaming.stafflab.builder.ReplaceableBuilder;
import me.bryangaming.stafflab.data.ServerData;
import me.bryangaming.stafflab.loader.file.FileManager;
import me.bryangaming.stafflab.managers.FreezeManager;
import me.bryangaming.stafflab.managers.InvseeManager;
import me.bryangaming.stafflab.managers.SenderManager;
import me.bryangaming.stafflab.managers.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataLoader implements Loader {

    private final StaffLab staffLab;
    private final FileManager configFile;

    private final SenderManager senderManager;
    private final VanishManager vanishManager;
    private final FreezeManager freezeManager;
    private final InvseeManager invseeManager;

    private final ServerData serverData;

    public DataLoader(PluginCore pluginCore) {
        this.staffLab = pluginCore.getPlugin();
        this.configFile = pluginCore.getFiles().getConfigFile();

        this.senderManager = pluginCore.getManagers().getSenderManager();
        this.vanishManager = pluginCore.getManagers().getVanishManager();
        this.freezeManager = pluginCore.getManagers().getFreezeManager();
        this.invseeManager = pluginCore.getManagers().getInvseeManager();

        this.serverData = pluginCore.getServerData();

        Map<String, GuiBuilder> staffGuiModeMap = serverData.getData();
        Map<Integer, ItemBuilder> staffInventoryMap = serverData.getInventoryData();

        staffGuiModeMap.clear();
        staffInventoryMap.clear();

    }

    @Override
    public void load() {

        GuiBuilder freezeGUIBuilder = GuiBuilder
                .create(configFile.getColoredString("gui.freeze.title"), 6);

        ItemBuilder freezeItemBuilder = ItemBuilder
                .create(Material.getMaterial(configFile.getString("inventory.freeze.material")))
                .setName(configFile.getColoredString("inventory.freeze.title"))
                .setLore(configFile.getColoredStringList("inventory.freeze.lore"))
                .setAction(event -> {

                    if (Bukkit.getOnlinePlayers().size() < 2){
                        senderManager.sendMessage(event, "freeze.error.one-player");
                        return;
                    }

                    Player player = Bukkit.getPlayer(event.getName());
                    player.setMetadata("staffguimode", new FixedMetadataValue(staffLab, "freeze"));

                    freezeGUIBuilder.clear();
                    for (Player onlineServerPlayer : Bukkit.getOnlinePlayers()) {
                        freezeGUIBuilder.addItem(
                                ItemBuilder
                                        .create(Material.getMaterial(configFile.getString("gui.freeze.player.material")))
                                        .setName(configFile.getColoredString("gui.freeze.player.title")
                                                .replace("%player%", onlineServerPlayer.getName()))
                                        .setLore(configFile.getColoredStringList("gui.freeze.player.lore"))
                                        .setAction(onlinePlayer -> {
                                            freezeManager.freezePlayer(onlineServerPlayer);
                                            onlinePlayer.closeInventory();
                                        }));
                    }

                    player.openInventory(freezeGUIBuilder.build());

                });

        ItemBuilder vanishItemBuilder = ItemBuilder
                .create(Material.getMaterial(configFile.getString("inventory.vanish.material")))
                .setName(configFile.getColoredString("inventory.vanish.title"))
                .setLore(configFile.getColoredStringList("inventory.vanish.lore"))
                .setAction(event -> {
                    Player player = Bukkit.getPlayer(event.getName());

                    if (!vanishManager.isPlayerVanished(player)) {
                        vanishManager.vanishPlayer(player);
                        senderManager.sendMessage(player, "vanish.player.enabled");
                    } else {
                        vanishManager.unVanishPlayer(player);
                        senderManager.sendMessage(player, "vanish.player.disabled");
                    }
                });

        ItemBuilder randomTPItemBuilder = ItemBuilder
                .create(Material.getMaterial(configFile.getString("inventory.random-tp.material")))
                .setName(configFile.getColoredString("inventory.random-tp.title"))
                .setLore(configFile.getColoredStringList("inventory.random-tp.lore"))
                .setAction(event -> {


                    List<Player> onlinePlayer = new ArrayList<>(Bukkit.getOnlinePlayers());

                    if (onlinePlayer.size() < 2){
                        senderManager.sendMessage(event, "random-tp.left");
                        return;
                    }

                    System.out.println(onlinePlayer.size());
                    event.teleport(onlinePlayer.get(new Random().nextInt(onlinePlayer.size()) - 1));
                    senderManager.sendMessage(event, "random-tp.target",
                            ReplaceableBuilder.create("%player%", event.getName()));
                });

        ItemBuilder invseeItemBuilder = ItemBuilder
                .create(Material.getMaterial(configFile.getString("inventory.invsee.material")))
                .setName(configFile.getColoredString("inventory.invsee.title"))
                .setLore(configFile.getColoredStringList("inventory.invsee.lore"))
                .setAction(event -> {
                    if (event.hasMetadata("invsee")){
                        return;
                    }
                    senderManager.sendMessage(event, "invsee.target-no-exists"); })
                .setEntityAction(event -> {

                    Player sender = event.getPlayer();

                    if (event.getEntity().getType() != EntityType.PLAYER){
                        return;
                    }

                    Player target = (Player) event.getEntity();
                    event.getPlayer().setMetadata("invsee", new FixedMetadataValue(staffLab, target.getName()));

                    if (!event.getPlayer().hasMetadata("invsee")){
                        senderManager.sendMessage(sender, "invsee.target-no-exists");
                        return;
                    }

                    invseeManager.seePlayerInventory(sender, Bukkit.getPlayer(target.getMetadata("invsee").get(0).asString()));

                });

        serverData
                .addPluginItem(configFile.getInt("inventory.freeze.id"), freezeItemBuilder)
                .addPluginItem(configFile.getInt("inventory.vanish.id"), vanishItemBuilder)
                .addPluginItem(configFile.getInt("inventory.random-tp.id"), randomTPItemBuilder)
                .addPluginItem(configFile.getInt("inventory.invsee.id"), invseeItemBuilder);
        serverData.addPluginGui("freeze", freezeGUIBuilder);
    }
}
