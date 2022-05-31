package me.james.knockbackffa.utils;

import me.james.knockbackffa.Main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class InventoryManager {

    public static void setupInventory(Player p) {
        Plugin plugin = Main.getPlugin(Main.class);
        PlayerInventory inv = p.getInventory();
        inv.setArmorContents(null);
        inv.clear();

        if (plugin.getConfig().isSet("invsort." + p.getUniqueId().toString())) {
            getInvSort(p);
        } else {
            ItemStack stick = new ItemStack(Material.STICK);
            stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
            ItemMeta stickMeta = stick.getItemMeta();
            stickMeta.setDisplayName("§bStick");
            stick.setItemMeta(stickMeta);

            ItemStack pearl = new ItemStack(Material.ENDER_PEARL, 4);

            ItemStack bow = new ItemStack(Material.BOW);
            bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
            ItemMeta bowMeta = bow.getItemMeta();
            bowMeta.setDisplayName("§bBow");
            bowMeta.spigot().setUnbreakable(true);
            bow.setItemMeta(bowMeta);

            ItemStack arrow = new ItemStack(Material.ARROW, 8);

            ItemStack blocks = new ItemStack(Material.SANDSTONE, 64);
            ItemMeta blocksMeta = blocks.getItemMeta();
            blocksMeta.setDisplayName("§bSandstone");
            blocks.setItemMeta(blocksMeta);

            inv.setItem(0, stick);
            inv.setItem(1, bow);
            inv.setItem(2, blocks);
            inv.setItem(7, arrow);
            inv.setItem(8, pearl);

            saveInvSort(p);
        }
    }

    public static void saveInvSort(Player p) {
        Plugin plugin = Main.getPlugin(Main.class);
        ItemStack[] items = p.getInventory().getContents();

        String encodedObject;

        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeObject(items);
            os.flush();

            byte[] serializedObject = io.toByteArray();

            encodedObject = Base64.getEncoder().encodeToString(serializedObject);

            plugin.getConfig().set("invsort." + p.getUniqueId().toString(), encodedObject);
            plugin.saveConfig();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void getInvSort(Player p) {
        Plugin plugin = Main.getPlugin(Main.class);
        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            byte[] serializedObject = io.toByteArray();

            serializedObject = Base64.getDecoder().decode(plugin.getConfig().getString("invsort." + p.getUniqueId().toString()));

            ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
            BukkitObjectInputStream is = new BukkitObjectInputStream(in);

            ItemStack[] items = (ItemStack[]) is.readObject();

            p.getInventory().setContents(items);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public static void giveSpawnItems(Player p) {
        Inventory inv = p.getInventory();

        inv.clear();

        ItemStack invsort = new ItemStack(Material.REDSTONE);
        ItemMeta invsortMeta = invsort.getItemMeta();
        invsortMeta.setDisplayName("§bInvsort");
        invsort.setItemMeta(invsortMeta);

        ItemStack cosmetics = new ItemStack(Material.CHEST);
        ItemMeta cosmeticsMeta = cosmetics.getItemMeta();
        cosmeticsMeta.setDisplayName("§bCosmetics");
        cosmetics.setItemMeta(cosmeticsMeta);

        inv.setItem(4, cosmetics);
        inv.setItem(5, invsort);
    }
}
