package me.james.knockbackffa.listeners;

import me.james.knockbackffa.utils.InventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getName().equals(ChatColor.AQUA + "Invsort")) {
            Player p = (Player) e.getPlayer();
            for (int i = 9; i < 36; i++) {
                p.getInventory().setItem(i, null);
            }
            InventoryManager.giveSpawnItems(p);
        } else if (e.getInventory().getName().equals(ChatColor.AQUA + "Cosmetics")) {
            Player p = (Player) e.getPlayer();
            InventoryManager.giveSpawnItems(p);
        }
    }
}
