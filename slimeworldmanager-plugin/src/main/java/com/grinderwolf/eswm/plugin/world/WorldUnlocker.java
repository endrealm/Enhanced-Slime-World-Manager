package com.grinderwolf.eswm.plugin.world;

import com.grinderwolf.eswm.api.exceptions.UnknownWorldException;
import com.grinderwolf.eswm.api.world.SlimeWorld;
import com.grinderwolf.eswm.plugin.SWMPlugin;
import com.grinderwolf.eswm.plugin.log.Logging;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;

import java.io.IOException;

public class WorldUnlocker implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldUnload(WorldUnloadEvent event) {
        SlimeWorld world = SWMPlugin.getInstance().getNms().getSlimeWorld(event.getWorld());

        if (world != null) {
            Bukkit.getScheduler().runTaskAsynchronously(SWMPlugin.getInstance(), () -> unlockWorld(world));
        }
    }

    private void unlockWorld(SlimeWorld world) {
        try {
            world.getLoader().unlockWorld(world.getName());
        } catch (IOException ex) {
            Logging.error("Failed to unlock world " + world.getName() + ". Retrying in 5 seconds. Stack trace:");
            ex.printStackTrace();

            Bukkit.getScheduler().runTaskLaterAsynchronously(SWMPlugin.getInstance(), () -> unlockWorld(world), 100);
        } catch (UnknownWorldException ignored) {

        }
    }
}
