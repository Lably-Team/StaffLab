package me.bryangaming.stafflab.builder;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InteractBuilder {

    private final Player player;
    private final Entity entity;

    public static InteractBuilder create(Player player, Entity entity) {
        return new InteractBuilder(player, entity);
    }

    private InteractBuilder(Player player, Entity entity){
        this.player = player;
        this.entity = entity;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity getEntity() {
        return entity;
    }
}
