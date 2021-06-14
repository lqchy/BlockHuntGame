package me.lachy.blockhunt.events;

import me.lachy.blockhunt.BHRunnableWinner;
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
import java.util.Objects;

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
            int group = getGroup(event.getPlayer());

            if (group == 1 || group == 2) {
                Material material = getBlock(group);

                if (group == 1 && block.getType().equals(material)) {
                    BHRunnableWinner.group1wins = true;
                }
                else if (group == 2 && block.getType().equals(material)) {
                    BHRunnableWinner.group2wins = true;
                }

            }

        }

    }

    private Material getBlock(int group) {
        FileConfiguration config = plugin.getConfig();
        return Material.valueOf(Objects.requireNonNull(config.getConfigurationSection("group" + group)).getString("block"));
    }

    private int getGroup(Player player) {

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
