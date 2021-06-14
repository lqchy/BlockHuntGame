package me.lachy.blockhunt.commands;

import me.lachy.blockhunt.BlockHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StopGameCommand implements CommandExecutor {

    private final BlockHunt plugin;

    public StopGameCommand(BlockHunt plugin) {
        this.plugin = plugin;
        plugin.getCommand("test").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.isOp() && plugin.getConfig().getBoolean("gameStarted")) {

                BlockHunt.blocksRunnable.cancel();
                BlockHunt.blocksRunnable.cancel();

                plugin.getConfig().set("gameStarted", false);
                int group1wins = plugin.getConfig().getInt("group1wins");
                int group2wins = plugin.getConfig().getInt("group2wins");

                String score;
                if (group1wins != group2wins) score = group1wins + " - " + group2wins + " to group " + (group1wins > group2wins ? 1 : 2) + ". Well played!";
                else score = group1wins + " - " + group2wins + ". A draw!";

                Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "Game over! The score was " + score);
                plugin.saveConfig();

            } else {
                player.sendMessage(ChatColor.RED + "Cannot stop a game that hasn't started!");
                return false;
            }

        }

        return true;
    }
}
