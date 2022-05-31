package me.james.knockbackffa.listeners;

import me.james.knockbackffa.Main;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        Plugin plugin = Main.getPlugin(Main.class);

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (p.getLocation().getY() >= 90) {
                e.setCancelled(true);
            }

            if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                CraftPlayer cp = (CraftPlayer) p;
                Bukkit.getScheduler().runTaskLater(plugin, () -> cp.getHandle().getDataWatcher().watch(9, (byte) 0), 1L);
            }

            if (e.getCause() == EntityDamageEvent.DamageCause.FALL || e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                e.setCancelled(true);
            }
        }
    }
}
