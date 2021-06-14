package me.lachy.blockhunt.commands;

import me.lachy.blockhunt.BlockHunt;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

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

                BlockHunt.runnable.runTaskTimer(plugin, 5 * 20, plugin.getConfig().getInt("roundTime") * 20L);
                plugin.getConfig().set("gameStarted", true);
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
