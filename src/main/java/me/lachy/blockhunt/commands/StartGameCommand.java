package me.lachy.blockhunt.commands;

import me.lachy.blockhunt.BlockHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class StartGameCommand implements CommandExecutor {

    private BlockHunt plugin;

    public StartGameCommand(BlockHunt plugin) {
        this.plugin = plugin;
        plugin.getCommand("start").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.isOp() && areGroupsSet()) {

                BlockHunt.blocksRunnable.runTaskTimer(plugin, 5 * 20, plugin.getConfig().getInt("roundTime") * 20L);
                BlockHunt.winnerRunnable.runTaskTimer(plugin, 5 * 20, 0);

                new BukkitRunnable() {

                    int seconds = 6;

                    @Override
                    public void run() {
                        if (seconds == 1) {
                            Bukkit.getOnlinePlayers().forEach(player1 ->
                                    player1.sendTitle(ChatColor.YELLOW + "Good luck!",
                                            "", 2, 40, 2));

                            ConfigurationSection group3 = plugin.getConfig().getConfigurationSection("group3");
                            if (group3 != null) {
                                for (String s : group3.getStringList("players")) {
                                    Player p = Bukkit.getPlayer(UUID.fromString(s));
                                    if (p != null) {
                                        p.setGameMode(GameMode.SPECTATOR);
                                    }
                                }
                            }

                            seconds--;
                        }

                        if (seconds > 1) {

                            Bukkit.getOnlinePlayers().forEach(player1 ->
                                    player1.sendTitle(ChatColor.GREEN + "Game starts in " + (seconds - 1) + " seconds!",
                                    "", 2, 20, 2));

                            seconds--;
                        }
                    }

                }.runTaskTimer(plugin, 0, 20);

                plugin.getConfig().set("gameStarted", true);
                plugin.getConfig().set("group1wins", 0);
                plugin.getConfig().set("group2wins", 0);
                plugin.saveConfig();

            } else {
                player.sendMessage(ChatColor.RED + "Cannot start - one (or more) group(s) is empty!");
                return false;
            }

        }

        return true;
    }

    private boolean areGroupsSet() {
        FileConfiguration config = BlockHunt.getInstance().getConfig();
        List<String> group1 = Objects.requireNonNull(config.getConfigurationSection("group1"))
                .getStringList("players");

        List<String> group2 = Objects.requireNonNull(config.getConfigurationSection("group1"))
                .getStringList("players");

        return group1.size() >= 1 && group2.size() >= 1;
    }

}
