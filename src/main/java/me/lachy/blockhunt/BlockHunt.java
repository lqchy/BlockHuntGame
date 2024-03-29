package me.lachy.blockhunt;

import me.lachy.blockhunt.commands.GroupCommand;
import me.lachy.blockhunt.commands.StartGameCommand;
import me.lachy.blockhunt.commands.StopGameCommand;
import me.lachy.blockhunt.events.BlockWalkListener;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public final class BlockHunt extends JavaPlugin {

    private static BlockHunt instance;
    public static BukkitRunnable blocksRunnable;
    public static BukkitRunnable winnerRunnable;

    @Override
    public void onEnable() {

        instance = this;
        blocksRunnable = new BHRunnableBlocks(this);
        winnerRunnable = new BHRunnableWinner(this);

        saveConfig();

        new StartGameCommand(this);
        new GroupCommand(this);
        new StopGameCommand(this);

        getServer().getPluginManager().registerEvents(new BlockWalkListener(this), this);

        FileConfiguration config = getConfig();

        if (!config.isConfigurationSection("group1")) {
            ConfigurationSection group1 = config.createSection("group1");
            group1.set("players", new ArrayList<String>());
            group1.set("block", null);
        }

        if (!config.isConfigurationSection("group2")) {
            ConfigurationSection group2 = config.createSection("group2");
            group2.set("players", new ArrayList<String>());
            group2.set("block", null);
        }

        if (!config.isConfigurationSection("group3")) {
            ConfigurationSection group2 = config.createSection("group3");
            group2.set("players", new ArrayList<String>());
        }

        config.set("roundTime", 300);
        config.set("gameStarted", false);

        saveConfig();
    }

    public static BlockHunt getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
