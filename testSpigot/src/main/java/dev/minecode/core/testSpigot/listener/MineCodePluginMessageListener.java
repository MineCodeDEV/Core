package dev.minecode.core.testSpigot.listener;

import dev.minecode.core.spigot.event.MineCodePluginMessageReceiveEvent;
import dev.minecode.core.testSpigot.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

public class MineCodePluginMessageListener implements Listener {

    public MineCodePluginMessageListener() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void handleMineCodePluginMessageReceive(MineCodePluginMessageReceiveEvent event) {
        System.out.println("sender: " + event.getSender());
        System.out.println("channel: " + event.getChannel());
        for (Map.Entry<String, String> entry : event.getMessage().entrySet()) {
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
        }
    }


}
