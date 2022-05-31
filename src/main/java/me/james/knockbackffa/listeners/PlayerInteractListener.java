package me.james.knockbackffa.listeners;

import me.james.knockbackffa.utils.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(e.getItem() == null || !e.getItem().hasItemMeta() || !e.getItem().getItemMeta().hasDisplayName()) return;

                if (e.getItem().getItemMeta().getDisplayName().equals("§bInvsort")) {
                    Inventory gui = Bukkit.createInventory(p, 9, ChatColor.AQUA + "Invsort");

                    ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
                    ItemMeta glassMeta = glass.getItemMeta();
                    glassMeta.setDisplayName(" ");
                    glass.setItemMeta(glassMeta);

                    for (int i = 0; i < 9; i++) {
                        gui.setItem(i, glass);
                    }

                    InventoryManager.setupInventory(p);

                    for (int i = 9; i < 36; i++) {
                        p.getInventory().setItem(i, glass);
                    }


                    ItemStack save = new ItemStack(Material.INK_SACK, 1, (byte) 10);
                    ItemMeta saveMeta = save.getItemMeta();
                    saveMeta.setDisplayName("§bSave Changes");
                    save.setItemMeta(saveMeta);

                    ItemStack dontSave = new ItemStack(Material.INK_SACK, 1, (byte) 1);
                    ItemMeta dontSaveMeta = save.getItemMeta();
                    dontSaveMeta.setDisplayName("§bDiscard Changes");
                    dontSave.setItemMeta(dontSaveMeta);

                    gui.setItem(3, save);
                    gui.setItem(5, dontSave);

                    glassMeta.setDisplayName(" ");
                    glass.setItemMeta(glassMeta);

                    p.openInventory(gui);

                    p.updateInventory();
                    e.setCancelled(true);
                } else if (e.getItem().getItemMeta().getDisplayName().equals("§bCosmetics")) {
                    if (!p.hasPermission("cosmetics.use")) {
                        p.sendMessage(ChatColor.RED + "Using cosmetics requires a rank! You can purchase a rank at https://refrains.tebex.io/");
                        e.setCancelled(true);
                        return;
                    }

                    Inventory gui = Bukkit.createInventory(p, 27, ChatColor.AQUA + "Cosmetics");

                    gui.setItem(0, getBlockCosmeticItem("Sandstone", Material.SANDSTONE, (byte)0));
                    gui.setItem(1, getBlockCosmeticItem("White Wool", Material.WOOL, (byte)0));
                    gui.setItem(2, getBlockCosmeticItem("Orange Wool", Material.WOOL, (byte)1));
                    gui.setItem(3, getBlockCosmeticItem("Magenta Wool", Material.WOOL, (byte)2));
                    gui.setItem(4, getBlockCosmeticItem("Light Blue Wool", Material.WOOL, (byte)3));
                    gui.setItem(5, getBlockCosmeticItem("Yellow Wool", Material.WOOL, (byte)4));
                    gui.setItem(6, getBlockCosmeticItem("Lime Wool", Material.WOOL, (byte)5));
                    gui.setItem(7, getBlockCosmeticItem("Pink Wool", Material.WOOL, (byte)6));
                    gui.setItem(8, getBlockCosmeticItem("Gray Wool", Material.WOOL, (byte)7));
                    gui.setItem(9, getBlockCosmeticItem("Light Gray Wool", Material.WOOL, (byte)8));
                    gui.setItem(10, getBlockCosmeticItem("Cyan Wool", Material.WOOL, (byte)9));
                    gui.setItem(11, getBlockCosmeticItem("Purple Wool", Material.WOOL, (byte)10));
                    gui.setItem(12, getBlockCosmeticItem("Blue Wool", Material.WOOL, (byte)11));
                    gui.setItem(13, getBlockCosmeticItem("Brown Wool", Material.WOOL, (byte)12));
                    gui.setItem(14, getBlockCosmeticItem("Green Wool", Material.WOOL, (byte)13));
                    gui.setItem(15, getBlockCosmeticItem("Red Wool", Material.WOOL, (byte)14));
                    gui.setItem(16, getBlockCosmeticItem("Black Wool", Material.WOOL, (byte)15));
                    gui.setItem(17, getBlockCosmeticItem("Hay Bale", Material.HAY_BLOCK, (byte)0));
                    gui.setItem(18, getBlockCosmeticItem("Oak Wood", Material.LOG, (byte)0));
                    gui.setItem(19, getBlockCosmeticItem("Spruce Wood", Material.LOG, (byte)1));
                    gui.setItem(20, getBlockCosmeticItem("Birch Wood", Material.LOG, (byte)2));
                    gui.setItem(21, getBlockCosmeticItem("Jungle Wood", Material.LOG, (byte)3));
                    gui.setItem(22, getBlockCosmeticItem("Acacia Wood", Material.LOG_2, (byte)0));
                    gui.setItem(23, getBlockCosmeticItem("Dark Oak Wood", Material.LOG_2, (byte)1));
                    gui.setItem(24, getBlockCosmeticItem("Melon", Material.MELON_BLOCK, (byte)0));
                    gui.setItem(25, getBlockCosmeticItem("Diamond Ore", Material.DIAMOND_ORE, (byte)0));
                    gui.setItem(26, getBlockCosmeticItem("Glass", Material.GLASS, (byte)0));

                    InventoryManager.setupInventory(p);
                    p.openInventory(gui);
                    e.setCancelled(true);
                }
            }
        }
    }

    public ItemStack getBlockCosmeticItem(String name, Material blockType, byte meta) {
        ItemStack item = new ItemStack(blockType, 1, meta);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + name);
        item.setItemMeta(itemMeta);
        return item;
    }
}
