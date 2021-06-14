package me.lachy.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class BHRunnableWinner extends BukkitRunnable {

    private BlockHunt plugin;

    public BHRunnableWinner(BlockHunt plugin) {
        this.plugin = plugin;
    }

    public static boolean group1wins;
    public static boolean group2wins;

    @Override
    public void run() {

        if (!plugin.getConfig().getBoolean("gameStarted")) cancel();

        if (group1wins) {
            announce(1, true);
        }

        else if (group2wins) {
            announce(2, true);
        }

    }

    public static void announce(int winningTeam, boolean restart) {
        switch (winningTeam) {
            case 1:
                Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "Group 1 won the round! Restarting..");
                group1wins = false;
                BlockHunt.getInstance().getConfig().set("group1wins", BlockHunt.getInstance().getConfig().getInt("group1wins") + 1);
                if (restart) {
                    BlockHunt.blocksRunnable.cancel();
                    BlockHunt.blocksRunnable.runTaskTimer(BlockHunt.getInstance(),
                            2 * 20, BlockHunt.getInstance().getConfig().getInt("roundTime") * 20L);
                }
                break;
            case 2:
                Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "Group 2 won the round! Restarting..");
                group2wins = false;
                BlockHunt.getInstance().getConfig().set("group2wins", BlockHunt.getInstance().getConfig().getInt("group2wins") + 1);
                if (restart) {
                    BlockHunt.blocksRunnable.cancel();
                    BlockHunt.blocksRunnable.runTaskTimer(BlockHunt.getInstance(),
                            2 * 20, BlockHunt.getInstance().getConfig().getInt("roundTime") * 20L);
                }
                break;
        }
    }
}
