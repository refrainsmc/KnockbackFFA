package me.james.knockbackffa.listeners;

import me.james.knockbackffa.utils.InventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
            if (e.getInventory().getName().equals(ChatColor.AQUA + "Invsort")) {
                e.getCurrentItem().setType(Material.AIR);
                e.setCancelled(true);
                InventoryManager.giveSpawnItems(p);
                p.closeInventory();
            }
        }

        if (e.getInventory().getName() == null) return;
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;
        if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        if (e.getInventory().getName().equals(ChatColor.AQUA + "Invsort")) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                e.setCancelled(true);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§bSave Changes")) {
                for (int i = 9; i < 36; i++) {
                    p.getInventory().setItem(i, null);
                }
                e.setCancelled(true);
                InventoryManager.saveInvSort(p);
                p.sendMessage(ChatColor.GREEN + "Saved Settings.");
                p.closeInventory();
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§bDiscard Changes")) {
                for (int i = 9; i < 36; i++) {
                    p.getInventory().setItem(i, null);
                }
                e.setCancelled(true);
                InventoryManager.giveSpawnItems(p);
                p.sendMessage(ChatColor.RED + "Discarded Settings.");
                p.closeInventory();
            } else {
                e.setCancelled(false);
            }
        } else if (e.getClickedInventory().getTitle().equals(ChatColor.AQUA + "Cosmetics")) {
            if (e.getCurrentItem().getType() != Material.AIR) {
                for(int i = 0; i < p.getInventory().getContents().length; i++) {
                    ItemStack item = p.getInventory().getItem(i);
                    if (item == null || item.getType() == null) continue;

                    if (item.getType().isBlock()) {
                        p.getInventory().setItem(i, e.getCurrentItem().clone());
                        p.getInventory().getItem(i).setAmount(64);
                    }
                }

                InventoryManager.saveInvSort(p);
                p.closeInventory();

                e.setCancelled(true);
            }
        } else {
            e.setCancelled(p.getGameMode() != GameMode.CREATIVE);
        }
    }
}
