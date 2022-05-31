package me.james.knockbackffa.listeners;

import me.james.knockbackffa.Main;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (e.getBlock().getLocation().getY() > 96) {
            e.setCancelled(true);
            return;
        }
        Main.deletingBlocks.put(e.getBlock().getLocation(), 0);

        if (e.getItemInHand().getAmount() < 32) {
            e.getItemInHand().setAmount(64);
        }
    }
}
