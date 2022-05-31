package me.james.knockbackffa;

import fr.mrmicky.fastboard.FastBoard;
import me.james.knockbackffa.listeners.*;
import me.james.knockbackffa.utils.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public static Map<Location, Integer> deletingBlocks = new HashMap<>();
    private static final Map<UUID, FastBoard> boards = new HashMap<>();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Scoreboard.setupUpdateBoardTask(this, Bukkit.getScheduler(), boards);

        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(), this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            HashSet<Location> bc = new HashSet<>();
            for (HashMap.Entry<Location, Integer> l : deletingBlocks.entrySet()) {
                int ticks = l.getValue();

                if (ticks == 100) {
                    Location loc = l.getKey();
                    Bukkit.getScheduler().runTask(this, () -> Bukkit.getWorld("world").getBlockAt(loc).setType(Material.REDSTONE_BLOCK));
                } else if (ticks == 120) {
                    Location loc = l.getKey();
                    Bukkit.getScheduler().runTask(this, () -> Bukkit.getWorld("world").getBlockAt(loc).setType(Material.AIR));

                    bc.add(loc);
                }
            }

            bc.forEach(l -> deletingBlocks.remove(l));
            deletingBlocks.replaceAll((l, v) -> v + 10);
        }, 0, 10);
    }

    public void onDisable() {
        for (HashMap.Entry<Location, Integer> l : deletingBlocks.entrySet()) {
            Bukkit.getWorld("world").getBlockAt(l.getKey()).setType(Material.AIR);
        }
        deletingBlocks.clear();
    }

    public static Map<UUID, FastBoard> getBoards() {
        return boards;
    }
}
