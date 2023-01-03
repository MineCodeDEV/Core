package dev.minecode.core.testBungeeCord.listener;

import dev.minecode.core.bungeecord.event.MineCodePluginMessageReceiveEvent;
import dev.minecode.core.testBungeeCord.TestBungeeCord;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Map;

public class MCPMReceiveListener implements Listener {

    public MCPMReceiveListener() {
        TestBungeeCord.getInstance().getProxy().getPluginManager().registerListener(TestBungeeCord.getInstance(), this);
    }

    @EventHandler
    public void handleMineCodePluginMessage(MineCodePluginMessageReceiveEvent event) {
        System.out.println("channel: " + event.getChannel());
        System.out.println("sender: " + event.getSender());
        for (Map.Entry<String, String> entry : event.getMessage().entrySet())
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
    }

}
