package com.grinderwolf.eswm.plugin.commands.sub;


import com.grinderwolf.eswm.api.exceptions.CorruptedWorldException;
import com.grinderwolf.eswm.api.exceptions.NewerFormatException;
import com.grinderwolf.eswm.api.exceptions.UnknownWorldException;
import com.grinderwolf.eswm.api.exceptions.WorldInUseException;
import com.grinderwolf.eswm.api.loaders.SlimeLoader;
import com.grinderwolf.eswm.api.world.SlimeWorld;
import com.grinderwolf.eswm.plugin.config.ConfigManager;
import com.grinderwolf.eswm.plugin.config.WorldData;
import com.grinderwolf.eswm.plugin.SWMPlugin;
import com.grinderwolf.eswm.plugin.commands.CommandManager;
import com.grinderwolf.eswm.plugin.config.WorldsConfig;
import com.grinderwolf.eswm.plugin.log.Logging;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.io.IOException;

@Getter
public class LoadWorldCmd implements Subcommand {

    private final String usage = "load <world>";
    private final String description = "Load a world.";
    private final String permission = "eswm.loadworld";

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length > 0) {
            String worldName = args[0];
            World world = Bukkit.getWorld(worldName);

            if (world != null) {
                sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.RED + "World " + worldName + " is already loaded!");

                return true;
            }

            WorldsConfig config = ConfigManager.getWorldConfig();
            WorldData worldData = config.getWorlds().get(worldName);

            if (worldData == null) {
                sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.RED + "Failed to find world " + worldName + " inside the worlds config file.");

                return true;
            }

            if (CommandManager.getInstance().getWorldsInUse().contains(worldName)) {
                sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.RED + "World " + worldName + " is already being used on another command! Wait some time and try again.");

                return true;
            }

            CommandManager.getInstance().getWorldsInUse().add(worldName);
            sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.GRAY + "Loading world " + ChatColor.YELLOW + worldName + ChatColor.GRAY + "...");

            // It's best to load the world async, and then just go back to the server thread and add it to the world list
            Bukkit.getScheduler().runTaskAsynchronously(SWMPlugin.getInstance(), () -> {

                try {
                    long start = System.currentTimeMillis();
                    SlimeLoader loader = SWMPlugin.getInstance().getLoader(worldData.getDataSource());

                    if (loader == null) {
                        throw new IllegalArgumentException("invalid data source " + worldData.getDataSource());
                    }

                    SlimeWorld slimeWorld = SWMPlugin.getInstance().loadWorld(loader, worldName, worldData.isReadOnly(), worldData.toPropertyMap());
                    Bukkit.getScheduler().runTask(SWMPlugin.getInstance(), () -> {
                        try {
                            SWMPlugin.getInstance().generateWorld(slimeWorld);
                        } catch (IllegalArgumentException ex) {
                            sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.RED + "Failed to generate world " + worldName + ": " + ex.getMessage() + ".");

                            return;
                        }

                        sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.GREEN + "World " + ChatColor.YELLOW + worldName
                                + ChatColor.GREEN + " loaded and generated in " + (System.currentTimeMillis() - start) + "ms!");
                    });
                } catch (CorruptedWorldException ex) {
                    if (!(sender instanceof ConsoleCommandSender)) {
                        sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.RED + "Failed to load world " + worldName +
                                ": world seems to be corrupted.");
                    }

                    Logging.error("Failed to load world " + worldName + ": world seems to be corrupted.");
                    ex.printStackTrace();
                } catch (NewerFormatException ex) {
                    sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.RED + "Failed to load world " + worldName + ": this world" +
                            " was serialized with a newer version of the Slime Format (" + ex.getMessage() + ") that E-SWM cannot understand.");
                } catch (UnknownWorldException ex) {
                    sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.RED + "Failed to load world " + worldName +
                            ": world could not be found (using data source '" + worldData.getDataSource() + "').");
                } catch (WorldInUseException ex) {
                    sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.RED + "Failed to load world " + worldName +
                            ": world is already in use. If you think this is a mistake, please wait some time and try again.");
                } catch (IllegalArgumentException ex) {
                    sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.RED + "Failed to load world " + worldName +
                            ": " + ex.getMessage());
                } catch (IOException ex) {
                    if (!(sender instanceof ConsoleCommandSender)) {
                        sender.sendMessage(Logging.COMMAND_PREFIX + ChatColor.RED + "Failed to load world " + worldName
                                + ". Take a look at the server console for more information.");
                    }

                    Logging.error("Failed to load world " + worldName + ":");
                    ex.printStackTrace();
                } finally {
                    CommandManager.getInstance().getWorldsInUse().remove(worldName);
                }
            });

            return true;
        }

        return false;
    }
}

