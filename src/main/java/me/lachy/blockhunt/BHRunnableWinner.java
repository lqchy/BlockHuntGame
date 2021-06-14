package me.lachy.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.graalvm.compiler.nodes.cfg.Block;

import java.util.HashMap;

public class BHRunnableWinner extends BukkitRunnable {

    private BlockHunt plugin;

    private static final HashMap<Integer, Long> cooldown = new HashMap<>();
    private static final int cooldowntime = 8;

    public BHRunnableWinner(BlockHunt plugin) {
        this.plugin = plugin;
    }

    public static boolean group1wins;
    public static boolean group2wins;

    @Override
    public void run() {

        if (!plugin.getConfig().getBoolean("gameStarted")) cancel();

        if (group1wins) {
            group1wins = false;
            int taskId = BlockHunt.blocksRunnable.getTaskId();
            Bukkit.getScheduler().cancelTask(taskId);
            announce(1, true);
        }

        else if (group2wins) {
            group2wins = false;
            int taskId = BlockHunt.blocksRunnable.getTaskId();
            Bukkit.getScheduler().cancelTask(taskId);
            announce(2, true);
        }

    }

    public static void announce(int winningTeam, boolean restart) {
        switch (winningTeam) {
            case 1:
                group1wins = false;
                Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "Group 1 won the round! Restarting..");
                BlockHunt.getInstance().getConfig().set("group1wins", BlockHunt.getInstance().getConfig().getInt("group1wins") + 1);
                BlockHunt.getInstance().saveConfig();
                if (restart) {
                    BlockHunt.getInstance().getConfig().set("gameStarted", true);
                    BlockHunt.getInstance().saveConfig();
                    if (BlockHunt.blocksRunnable.isCancelled())
                        BlockHunt.blocksRunnable = new BHRunnableBlocks(BlockHunt.getInstance());
                    BlockHunt.blocksRunnable.runTaskTimer(BlockHunt.getInstance(), 0, 5*20*60);
                }
                break;
            case 2:
                group2wins = false;
                Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "Group 2 won the round! Restarting..");
                BlockHunt.getInstance().getConfig().set("group2wins", BlockHunt.getInstance().getConfig().getInt("group2wins") + 1);
                BlockHunt.getInstance().saveConfig();
                if (restart) {
                    BlockHunt.getInstance().getConfig().set("gameStarted", true);
                    BlockHunt.getInstance().saveConfig();
                    if (BlockHunt.blocksRunnable.isCancelled())
                        BlockHunt.blocksRunnable = new BHRunnableBlocks(BlockHunt.getInstance());
                        BlockHunt.blocksRunnable.runTaskTimer(BlockHunt.getInstance(), 0, 5*20*60);
                }
                break;
        }
    }
}
