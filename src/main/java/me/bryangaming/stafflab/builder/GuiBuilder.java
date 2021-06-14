package me.bryangaming.stafflab.builder;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class GuiBuilder {

    private final String title;
    private final int size;

    private final Map<Integer, ItemBuilder> itemBuilerMap = new HashMap<>();

    public GuiBuilder(String title, int chestSize){
        this.title = title;
        this.size = chestSize * 9;
    }

    public static GuiBuilder create(String title, int chestSize){
        return new GuiBuilder(title, chestSize);

    }
    public ItemBuilder getItem(int id){
        return itemBuilerMap.get(id);
    }

    public GuiBuilder addItem(ItemBuilder itemBuilder){
        itemBuilerMap.put(itemBuilerMap.size(), itemBuilder);
        return this;
    }


    public GuiBuilder clear(){
        itemBuilerMap.clear();
        return this;
    }


    public Inventory build(){
        Inventory inventory = Bukkit.createInventory(null, size, title);
        for (int key : itemBuilerMap.keySet()){
            inventory.setItem(key, itemBuilerMap.get(key).build());
        }
        return inventory;
    }
}
