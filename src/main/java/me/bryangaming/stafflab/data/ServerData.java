package me.bryangaming.stafflab.data;

import me.bryangaming.stafflab.builder.GuiBuilder;
import me.bryangaming.stafflab.builder.ItemBuilder;

import java.util.HashMap;
import java.util.Map;

public class ServerData {

    private final Map<String, GuiBuilder> staffListGuiHashMap = new HashMap<>();
    private final Map<Integer, ItemBuilder> staffInventoryHashMap = new HashMap<>();


    public void putGui(String staffGuiMode, GuiBuilder guiBuilder){
        staffListGuiHashMap.put(staffGuiMode, guiBuilder);
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
