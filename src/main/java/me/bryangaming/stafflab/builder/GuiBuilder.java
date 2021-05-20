package me.bryangaming.stafflab.builder;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiBuilder {

    private final String title;
    private final int size;

    private final List<ItemBuilder> itemStackList = new ArrayList<>();

    public GuiBuilder(String title, int chestSize){
        this.title = title;
        this.size = chestSize * 9;
    }

    public static GuiBuilder create(String title, int chestSize){
        return new GuiBuilder(title, chestSize);

    }
    public ItemBuilder getItem(int id){
        return itemStackList.get(id);
    }

    public GuiBuilder addItem(ItemBuilder itemBuilder){
        itemStackList.add(itemBuilder);
        return this;
    }

    public GuiBuilder addItems(ItemBuilder... itemBuilders){
        itemStackList.addAll(Arrays.asList(itemBuilders));
        return this;
    }

    public GuiBuilder clear(){
        itemStackList.clear();
        return this;
    }


    public Inventory build(){
        Inventory inventory = Bukkit.createInventory(null, size, title);
        for (ItemBuilder itemBuilder : itemStackList){
            inventory.addItem(itemBuilder.build());
        }
        return inventory;
    }
}
