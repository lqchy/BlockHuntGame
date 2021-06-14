package me.lachy.blockhunt.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class BlockWalkListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {

        Location location = event.getTo();
        if (location != null) {
            Block block = location.getBlock();
            int team = getTeam(event.getPlayer());


        }

    }

    private int getTeam(Player player) {



    }


}
