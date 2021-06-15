package me.lachy.blockhunt.commands;

import me.lachy.blockhunt.BlockHunt;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class GroupCommand implements CommandExecutor, TabCompleter {

    private BlockHunt plugin;

    public GroupCommand(BlockHunt plugin) {
        this.plugin = plugin;
        plugin.getCommand("group").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args[0].equalsIgnoreCase("join")) {

            if (!Double.isNaN(Double.parseDouble(args[1])) && Integer.parseInt(args[1]) <= 3) {

                boolean team = joinGroup((Player) sender, args[1]);
                if (team) sender.sendMessage(ChatColor.GREEN + "Joined group " + Integer.parseInt(args[1]));
                else sender.sendMessage("something went wrong");
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid group number.");
            }

        } else if (args[0].equalsIgnoreCase("leave")) {
            if (!Double.isNaN(Double.parseDouble(args[1])) && Integer.parseInt(args[1]) <= 3) {
                boolean team = leaveGroup((Player) sender, args[1]);
                if (team) sender.sendMessage(ChatColor.GREEN + "Joined group " + Integer.parseInt(args[1]));
                else sender.sendMessage(ChatColor.RED + "Something went wrong.");
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid group number.");
            }
        }

        return true;
    }

    private boolean joinGroup(Player player, String group) {
        FileConfiguration config = plugin.getConfig();
        int groupNum = Integer.parseInt(group);

        ConfigurationSection section = config.getConfigurationSection("group" + groupNum);
        List<String> players;
        boolean success = false;
        if (section != null) {
            players = section.getStringList("players");
            if (!players.contains(player.getUniqueId().toString())){
                players.add(player.getUniqueId().toString());
                section.set("players", players);
                plugin.saveConfig();
                success = true;
            }
        }

        plugin.saveConfig();

        return success;
    }

    private boolean leaveGroup(Player player, String group) {
        FileConfiguration config = plugin.getConfig();
        int groupNum = Integer.parseInt(group);

        ConfigurationSection section = config.getConfigurationSection("group" + groupNum);
        List<String> players;
        boolean success = false;
        if (section != null) {
            players = section.getStringList("players");
            if (players.contains(player.getUniqueId().toString())){
                players.remove(player.getUniqueId().toString());
                section.set("players", players);
                plugin.saveConfig();
                success = true;
            }
        }

        plugin.saveConfig();

        return success;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) return Arrays.asList("join", "leave");
        else return null;
    }
}
