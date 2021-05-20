package me.bryangaming.stafflab.module;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.api.Module;
import me.bryangaming.stafflab.builder.GuiBuilder;
import me.bryangaming.stafflab.builder.ItemBuilder;
import me.bryangaming.stafflab.loader.file.FileManager;
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

    private final Map<String, GuiBuilder> staffGuiModeMap;
    private final Map<Integer, ItemBuilder> staffInventoryMap;

    public DataModule(PluginCore pluginCore) {
        this.staffLab = pluginCore.getPlugin();
        this.configFile = pluginCore.getFiles().getConfigFile();

        this.senderManager = pluginCore.getSenderManager();
        this.staffGuiModeMap = pluginCore.getServerData().getData();
        this.staffInventoryMap = pluginCore.getServerData().getInventoryData();
    }

    @Override
    public void load() {
        staffGuiModeMap.clear();
        staffInventoryMap.clear();

        GuiBuilder guiBuilder = GuiBuilder
                .create(configFile.getColoredString("gui.freeze.title"), 6);

        staffInventoryMap.put(configFile.getInt("inventory.freeze.id"), ItemBuilder
                .create(Material.getMaterial(configFile.getString("inventory.freeze.material")))
                .setName(configFile.getColoredString("inventory.freeze.title"))
                .setLore(configFile.getColoredStringList("inventory.freeeze.lore"))
                .setAction(event -> {

                    event.getWhoClicked().setMetadata("staffguimode", new FixedMetadataValue(staffLab, "freeze"));

                    guiBuilder.clear();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        guiBuilder.addItem(
                                ItemBuilder
                                        .create(Material.getMaterial(configFile.getString("gui.freeze.player.material")))
                                        .setName(configFile.getColoredString("gui.freeze.player.title")
                                                .replace("%player%", player.getName()))
                                        .setLore(configFile.getColoredStringList("gui.freeze.player.lore"))
                                        .setAction(onlinePlayer -> senderManager.));
                    }
                    event.getWhoClicked().openInventory(guiBuilder.build());
                }));


        staffGuiModeMap.put("staffguimode", guiBuilder);
    }
}
