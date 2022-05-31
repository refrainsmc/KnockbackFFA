package me.james.knockbackffa.listeners;

import me.james.knockbackffa.Main;
import me.james.knockbackffa.utils.Scoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Scoreboard.deleteBoard(e.getPlayer(), Main.getBoards());
    }
}