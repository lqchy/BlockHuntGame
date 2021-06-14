package me.lachy.blockhunt.events;

import me.lachy.blockhunt.BlockHunt;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class BlockWalkListener implements Listener {

    private final BlockHunt plugin;

    public BlockWalkListener(BlockHunt plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {

        Location location = event.getTo();
        if (location != null) {
            Block block = location.getBlock();
            int team = getTeam(event.getPlayer());

            if (team == 1 || team == 2) {
                Material material = getBlock(team);

                if (team == 1 && block.getType().equals(material)) {

                }

            }

        }

    }

    private Material getBlock(int team) {
        FileConfiguration config = plugin.getConfig();
        return Material.valueOf(config.getConfigurationSection("group" + team).getString("block"));
    }

    private int getTeam(Player player) {

        FileConfiguration config = plugin.getConfig();
        ConfigurationSection group1 = config.getConfigurationSection("group1");
        ConfigurationSection group2 = config.getConfigurationSection("group2");

        List<String> group1players = group1 != null ? group1.getStringList("players") : null;
        List<String> group2players = group2 != null ? group2.getStringList("players") : null;

        if (group1players != null && group1players.contains(player.getUniqueId().toString())) return 1;
        if (group2players != null && group2players.contains(player.getUniqueId().toString())) return 2;
        else return 0;

    }


}
