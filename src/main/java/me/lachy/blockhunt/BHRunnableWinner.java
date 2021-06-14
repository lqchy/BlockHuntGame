package me.lachy.blockhunt;

import org.bukkit.scheduler.BukkitRunnable;

public class BHRunnableWinner extends BukkitRunnable {

    public boolean group1wins;
    public boolean group2wins;

    @Override
    public void run() {

        if (group1wins) {
            announce(1, true);
        }

        else if (group2wins) {
            announce(2, true);
        }

    }

    public static void announce(int winningTeam, boolean restart) {

    }
}
