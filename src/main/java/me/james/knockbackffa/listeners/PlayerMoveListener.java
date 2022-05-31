package me.james.knockbackffa.listeners;

import me.james.knockbackffa.utils.InventoryManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE) {
            if (e.getTo().getY() < 96 && e.getTo().getY() > 60) {
                if (!p.getInventory().contains(Material.STICK)) {
                    InventoryManager.setupInventory(p);
                }
            }
            if (e.getTo().getY() < 60) {
                p.setHealth(20);
                p.getActivePotionEffects().clear();

                Location loc = new Location(p.getWorld(), 0.5, 100, 0.5);
                p.teleport(loc);

                InventoryManager.giveSpawnItems(p);
            }
        }
    }
}
