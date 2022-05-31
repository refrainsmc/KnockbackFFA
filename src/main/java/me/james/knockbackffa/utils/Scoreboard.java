package me.james.knockbackffa.utils;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Map;
import java.util.UUID;

public class Scoreboard {
    public static void setupUpdateBoardTask(Plugin plugin, BukkitScheduler scheduler, Map<UUID, FastBoard> boards) {
        scheduler.runTaskTimer(plugin, () -> {
            for (FastBoard board : boards.values())
                updateBoard(board);
        }, 0L, 5L);
    }

    public static void createBoard(Player player, Map<UUID, FastBoard> boards) {
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.AQUA + "Refrains");
        boards.put(player.getUniqueId(), board);
    }

    public static void deleteBoard(Player player, Map<UUID, FastBoard> boards) {
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    public static void updateBoard(FastBoard board) {
        board.updateLines(
                "" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "------------------------",
                ChatColor.AQUA + "Playing:",
                "" + Bukkit.getServer().getOnlinePlayers().size(),
                "",
                ChatColor.AQUA + "refrains.club",
                "" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "------------------------"
        );
    }
}
