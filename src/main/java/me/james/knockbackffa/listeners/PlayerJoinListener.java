package me.james.knockbackffa.listeners;

import me.james.knockbackffa.Main;
import me.james.knockbackffa.utils.InventoryManager;
import me.james.knockbackffa.utils.Scoreboard;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);

        for(PotionEffect potion : p.getActivePotionEffects()) {
            p.removePotionEffect(potion.getType());
        }

        p.setFlying(false);
        p.setFoodLevel(20);

        Location loc = new Location(p.getWorld(), 0.5, 100, 0.5);
        p.teleport(loc);

        InventoryManager.giveSpawnItems(p);
        Scoreboard.createBoard(p, Main.getBoards());
    }
}
