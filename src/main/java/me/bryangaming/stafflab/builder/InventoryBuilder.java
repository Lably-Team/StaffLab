package me.bryangaming.stafflab.builder;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder {

    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;

    private ItemStack[] items;

    public static InventoryBuilder create(){
        return new InventoryBuilder();
    }

    public InventoryBuilder setItems(ItemStack[] items){
        this.items = items;
        return this;
    }

    public InventoryBuilder setHelmet(ItemStack helmet){
        this.helmet = helmet;
        return this;
    }

    public InventoryBuilder setChestplate(ItemStack chestplate){
        this.chestplate = chestplate;
        return this;
    }

    public InventoryBuilder setLeggings(ItemStack leggings){
        this.leggings = leggings;
        return this;
    }

    public InventoryBuilder setBoots(ItemStack boots){
        this.boots = boots;
        return this;
    }

    public void givePlayer(Player player){
        Inventory inventory = player.getInventory();
        inventory.clear();

        inventory.addItem(items);
        EntityEquipment entityEquipment = player.getEquipment();
        entityEquipment.setHelmet(helmet);
        entityEquipment.setChestplate(chestplate);
        entityEquipment.setLeggings(leggings);
        entityEquipment.setBoots(boots);

    }


}
