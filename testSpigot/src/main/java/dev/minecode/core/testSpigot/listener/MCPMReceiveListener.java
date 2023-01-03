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
        System.out.println("channel: " + event.getChannel());
        System.out.println("sender: " + event.getSender());
        for (java.util.Map.Entry<String, String> entry : event.getMessage().entrySet())
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
    }

}
