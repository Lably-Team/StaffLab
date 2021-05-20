package me.bryangaming.stafflab.data;

import me.bryangaming.stafflab.builder.GuiBuilder;
import me.bryangaming.stafflab.builder.ItemBuilder;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerData {

    private final List<Player> vanishedPlayers = new ArrayList<>();

    private final Map<String, GuiBuilder> staffListGuiHashMap = new HashMap<>();
    private final Map<Integer, ItemBuilder> staffInventoryHashMap = new HashMap<>();


    public void putGui(String staffGuiMode, GuiBuilder guiBuilder){
        staffListGuiHashMap.put(staffGuiMode, guiBuilder);
    }

    public void addVanishedPlayer(Player player){
        vanishedPlayers.add(player);
    }

    public void removeVanishedPlayer(Player player){
        vanishedPlayers.remove(player);
    }

    public boolean isPlayerVanished(Player player){
        return vanishedPlayers.contains(player);
    }

    public GuiBuilder getGUI(String staffGuiMode){
        return staffListGuiHashMap.get(staffGuiMode);
    }

    public void addInventory(int integer, ItemBuilder itemBuilder){
        staffInventoryHashMap.put(integer, itemBuilder);
    }

    public Map<String, GuiBuilder> getData(){
        return staffListGuiHashMap;
    }

    public Map<Integer, ItemBuilder> getInventoryData(){
        return staffInventoryHashMap;
    }
}
