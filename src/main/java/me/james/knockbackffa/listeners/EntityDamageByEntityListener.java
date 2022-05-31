package me.james.knockbackffa.listeners;

import me.james.knockbackffa.utils.InventoryManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if((p.getHealth() - e.getDamage() < 1)) {
                e.setCancelled(true);

                p.setHealth(20);
                p.getActivePotionEffects().clear();

                Location loc = new Location(p.getWorld(), 0.5, 100, 0.5);
                p.teleport(loc);

                InventoryManager.giveSpawnItems(p);
            }
        }
    }
}
