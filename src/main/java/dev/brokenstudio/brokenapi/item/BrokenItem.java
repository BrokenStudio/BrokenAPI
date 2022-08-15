package dev.brokenstudio.brokenapi.item;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Arrays;

/*
    Project: ArcticLib
    File: ArcticItem
    Created by: Jan Z.
    Created at: 3/27/2022

    © 2022 BrokenStudio x Jan
 */
public class BrokenItem extends ItemStack {

    public BrokenItem(Material material){
        super(material);
    }

    public BrokenItem(ItemStack itemStack){
        super(itemStack);
    }

    public BrokenItem amount(int amount){
        setAmount(amount);
        return this;
    }

    public BrokenItem name(String name){
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(name);
        setItemMeta(meta);
        return this;
    }

    public BrokenItem configName(String name){
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',name));
        setItemMeta(meta);
        return this;
    }

    public BrokenItem lore(String... lore){
        ItemMeta meta = getItemMeta();
        meta.setLore(Arrays.asList(lore));
        setItemMeta(meta);
        return this;
    }

    public BrokenItem durability(int durability){
        setDurability((short) durability);
        return this;
    }

    public BrokenItem enchant(Enchantment enchantment){
        addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public BrokenItem enchant(Enchantment enchantment, int level){
        addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public BrokenItem glow(){
        addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        setItemMeta(meta);
        return this;
    }

    public BrokenItem glow(boolean glow){
        if(glow){
            glow();
        }
        return this;
    }

    public BrokenItem addItemFlags(ItemFlag... flags){
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(flags);
        setItemMeta(meta);
        return this;
    }

    public BrokenItem unbreakable(){
        ItemMeta meta = getItemMeta();
        meta.setUnbreakable(true);
        setItemMeta(meta);
        return this;
    }

    public BrokenItem hideAttributes(){
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        setItemMeta(meta);
        return this;
    }

    public BrokenItem color(Color color){
        if (getType() == Material.LEATHER_BOOTS || getType() == Material.LEATHER_CHESTPLATE
                || getType() == Material.LEATHER_HELMET || getType() == Material.LEATHER_LEGGINGS) {
            LeatherArmorMeta meta = (LeatherArmorMeta) getItemMeta();
            meta.setColor(color);
            setItemMeta(meta);
            return this;
        }
        if (getType() == Material.POTION) {
            PotionMeta potionMeta = (PotionMeta) getItemMeta();
            potionMeta.setColor(color);
            setItemMeta(potionMeta);
            return this;
        } else {
            throw new IllegalArgumentException("Color only applicable for leather armor and potions!");
        }
    }



}
