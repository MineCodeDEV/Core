package dev.minecode.core.testSpigot.listener;

import dev.minecode.core.testSpigot.TestSpigot;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MCPMReceiveListener implements Listener {

    public MCPMReceiveListener() {
        Bukkit.getPluginManager().registerEvents(this, TestSpigot.getInstance());
    }

    @EventHandler
    public void handleMineCodePluginMessage(dev.minecode.core.spigot.event.MineCodePluginMessageReceiveEvent event) {
        Bukkit.getLogger().info("channel: " + event.getChannel());
        Bukkit.getLogger().info("sender: " + event.getSender());
        for (java.util.Map.Entry<String, String> entry : event.getMessage().entrySet())
            Bukkit.getLogger().info("key: " + entry.getKey() + " value: " + entry.getValue());
    }

}
