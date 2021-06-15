package me.lachy.blockhunt;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static me.lachy.blockhunt.BHRunnableWinner.announce;

public class BHRunnableBlocks extends BukkitRunnable {

    private final BlockHunt blockHunt;

    public BHRunnableBlocks(BlockHunt plugin) {
        this.blockHunt = plugin;
    }

    @Override
    public void run() {

        if (blockHunt.getConfig().getBoolean("gameStarted")) {

            ConfigurationSection group1 = blockHunt.getConfig().isConfigurationSection("group1")
                    ? blockHunt.getConfig().getConfigurationSection("group1") : null;

            ConfigurationSection group2 = blockHunt.getConfig().isConfigurationSection("group2")
                    ? blockHunt.getConfig().getConfigurationSection("group2") : null;

            ConfigurationSection group3 = blockHunt.getConfig().isConfigurationSection("group3")
                    ? blockHunt.getConfig().getConfigurationSection("group3") : null;

            List<String> t1players;
            List<String> t2players;
            List<String> t3players;

            if (group1 != null) {
                t1players = group1.getStringList("players");
                List<String> values = blockHunt.getConfig().getStringList("blocks");
                Material block = Material.valueOf(values.get((int) Math.floor(Math.random() * values.size())));
                String name = block.name();
                group1.set("block", name);
                t1players.forEach(s -> {
                    Player player = Bukkit.getPlayer(UUID.fromString(s));
                    if (player != null) {
                        player.sendMessage(ChatColor.GREEN + "Your block is "
                                + ChatColor.GOLD + WordUtils.capitalizeFully(group1.getString("block").toLowerCase()
                                .replace("_", " ")));
                    }
                });
                blockHunt.saveConfig();
            }

            if (group2 != null) {
                t2players = group2.getStringList("players");
                List<String> values = blockHunt.getConfig().getStringList("blocks");
                Material block = Material.valueOf(values.get((int) Math.floor(Math.random() * values.size())));
                String name = block.name();
                group2.set("block", name);
                t2players.forEach(s -> {
                    Player player = Bukkit.getPlayer(UUID.fromString(s));
                    if (player != null) {
                        player.sendMessage(ChatColor.GREEN + "Your block is "
                            + ChatColor.GOLD + WordUtils.capitalizeFully(group2.getString("block").toLowerCase()
                            .replace("_", " ")));
                    }
                });
                blockHunt.saveConfig();
            }

            if (group3 != null) {
                t3players = group3.getStringList("players");
                t3players.forEach(s -> {
                    Player player = Bukkit.getPlayer(UUID.fromString(s));
                    if (player != null) {
                        if (group1 != null) {
                            player.sendMessage(ChatColor.GREEN + "Group 1's block is "
                                    + ChatColor.GOLD + WordUtils.capitalizeFully(group1.getString("block").toLowerCase()
                                    .replace("_", " ")));
                        }

                        if (group2 != null) {
                            player.sendMessage(ChatColor.GREEN + "Group 2's block is "
                                    + ChatColor.GOLD + WordUtils.capitalizeFully(group2.getString("block").toLowerCase()
                                    .replace("_", " ")));
                        }
                    }
                });
            }

//            if (!BHRunnableWinner.group1wins && !BHRunnableWinner.group2wins) {
//                int taskId = BlockHunt.blocksRunnable.getTaskId();
//                Bukkit.getScheduler().cancelTask(taskId);
//                announce(0, true);
//            }

        } else {
            cancel();
        }

    }
}
