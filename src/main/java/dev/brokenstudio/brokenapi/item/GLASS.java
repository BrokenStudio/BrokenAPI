package dev.brokenstudio.brokenapi.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum GLASS {
    WHITE(Material.WHITE_STAINED_GLASS),
    ORANGE(Material.ORANGE_STAINED_GLASS),
    MAGENTA(Material.MAGENTA_STAINED_GLASS),
    LIGHT_BLUE(Material.LIGHT_BLUE_STAINED_GLASS),
    YELLOW(Material.YELLOW_STAINED_GLASS),
    LIME(Material.LIME_STAINED_GLASS),
    PINK(Material.PINK_STAINED_GLASS),
    GRAY(Material.GRAY_STAINED_GLASS),
    LIGHT_GRAY(Material.LIGHT_GRAY_STAINED_GLASS),
    CYAN(Material.CYAN_STAINED_GLASS),
    PURPLE(Material.PURPLE_STAINED_GLASS),
    BLUE(Material.BLUE_STAINED_GLASS),
    BROWN(Material.BROWN_STAINED_GLASS),
    GREEN(Material.GREEN_STAINED_GLASS),
    RED(Material.RED_STAINED_GLASS),
    BLACK(Material.BLACK_STAINED_GLASS);

    private final Material material;

    GLASS(Material material){
        this.material = material;
    }

    public Material getMaterial(){
        return material;
    }

    public ItemStack item(){
        return new BrokenItem(material).name(" ");
    }

}
