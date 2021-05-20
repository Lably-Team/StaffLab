package me.bryangaming.stafflab.builder;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {

    private final Material material;

    private int itemID;

    private String itemName;
    private List<String> itemLore;

    private Consumer<InventoryClickEvent> action;


    public ItemBuilder(Material material){
        this.material = material;
    }

    public static ItemBuilder create(Material material){
        return new ItemBuilder(material);

    }

    public ItemBuilder setItemID(int itemID){
        this.itemID = itemID;
        return this;
    }

    public ItemBuilder setName(String itemName){
        this.itemName = itemName;
        return this;
    }

    public ItemBuilder setLore(List<String> itemLore){
        this.itemLore = itemLore;
        return this;
    }

    public ItemBuilder setAction(Consumer<InventoryClickEvent> action){
        this.action = action;
        return this;
    }

    public void callAction(InventoryClickEvent event){
        if (action == null){
            return;
        }
        action.accept(event);
    }

    public ItemStack build(){
        ItemStack itemStack = new ItemStack(material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(itemLore);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
