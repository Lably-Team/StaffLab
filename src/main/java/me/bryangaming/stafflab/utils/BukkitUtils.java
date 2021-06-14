package me.bryangaming.stafflab.utils;

import javafx.print.PageLayout;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;

public class BukkitUtils {


    public static void addUnlimitedEffects(Player player, PotionEffectType... potionEffectTypes){
        for (PotionEffectType potionEffectType : potionEffectTypes){
            switch (potionEffectType.getName()){
                case "BLINDNESS":
                    player.addPotionEffect(new PotionEffect(potionEffectType, 10000000, 1));
                    continue;
                default:
                    player.addPotionEffect(new PotionEffect(potionEffectType, 10000000, 200));

            }
        }
    }

    public static void removePotionEffects(Player player, PotionEffectType... potionEffectTypes){
        for (PotionEffectType potionEffectType : potionEffectTypes){
            player.removePotionEffect(potionEffectType);
        }
    }
}
