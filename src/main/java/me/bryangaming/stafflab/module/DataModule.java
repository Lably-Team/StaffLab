package me.bryangaming.stafflab.module;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.api.Module;
import me.bryangaming.stafflab.builder.GuiBuilder;
import me.bryangaming.stafflab.builder.ItemBuilder;
import me.bryangaming.stafflab.data.ServerData;
import me.bryangaming.stafflab.loader.file.FileManager;
import me.bryangaming.stafflab.managers.FreezeManager;
import me.bryangaming.stafflab.managers.SenderManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Map;

public class DataModule implements Module {

    private final StaffLab staffLab;
    private final FileManager configFile;

    private final SenderManager senderManager;
    private final FreezeManager freezeManager;

    private final ServerData serverData;

    private final Map<String, GuiBuilder> staffGuiModeMap;
    private final Map<Integer, ItemBuilder> staffInventoryMap;

    public DataModule(PluginCore pluginCore) {
        this.staffLab = pluginCore.getPlugin();
        this.configFile = pluginCore.getFiles().getConfigFile();

        this.senderManager = pluginCore.getManagers().getSenderManager();
        this.freezeManager = pluginCore.getManagers().getFreezeManager();

        this.serverData = pluginCore.getServerData();
        this.staffGuiModeMap = serverData.getData();
        this.staffInventoryMap = serverData.getInventoryData();

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
                .setLore(configFile.getColoredStringList("inventory.freeeze.lore"))
                .setAction(event -> {

                    event.getWhoClicked().setMetadata("staffguimode", new FixedMetadataValue(staffLab, "freeze"));

                    freezeGUIBuilder.clear();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        freezeGUIBuilder.addItem(
                                ItemBuilder
                                        .create(Material.getMaterial(configFile.getString("gui.freeze.player.material")))
                                        .setName(configFile.getColoredString("gui.freeze.player.title")
                                                .replace("%player%", player.getName()))
                                        .setLore(configFile.getColoredStringList("gui.freeze.player.lore"))
                                        .setAction(onlinePlayer -> freezeManager.freezePlayer(player)));
                    }
                    event.getWhoClicked().openInventory(freezeGUIBuilder.build());
                });

        serverData.addInventory(configFile.getInt("inventory.freeze.id"), freezeItemBuilder);
        serverData.putGui("staffguimode", freezeGUIBuilder);
    }
}
