package dev.minecode.core.testBungeeCord.listener;

import dev.minecode.core.bungeecord.CoreBungeeCord;
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
        CoreBungeeCord.getInstance().getMainClass().getLogger().info("channel: " + event.getChannel());
        CoreBungeeCord.getInstance().getMainClass().getLogger().info("sender: " + event.getSender());
        for (Map.Entry<String, String> entry : event.getMessage().entrySet())
            CoreBungeeCord.getInstance().getMainClass().getLogger().info("key: " + entry.getKey() + " value: " + entry.getValue());
    }

}
