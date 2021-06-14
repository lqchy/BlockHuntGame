package me.lachy.blockhunt.commands;

import me.lachy.blockhunt.BlockHunt;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestCommand implements CommandExecutor {

    private BlockHunt plugin;

    public TestCommand(BlockHunt plugin) {
        this.plugin = plugin;
        plugin.getCommand("test").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        for (String m : plugin.getConfig().getStringList("blocks")) {
            ((Player) sender).getInventory().addItem(new ItemStack(Material.valueOf(m)));
        }


        return true;
    }
}
