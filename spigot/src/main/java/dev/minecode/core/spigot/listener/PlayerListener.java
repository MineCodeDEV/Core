package dev.minecode.core.spigot.listener;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.common.object.PluginMessage;
import dev.minecode.core.spigot.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    public PlayerListener() {
        Bukkit.getPluginManager().registerEvents(this, CoreSpigot.getInstance().getMainClass());
    }

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        if (Bukkit.getOnlinePlayers().size() == 0) {
            for (PluginMessage pluginMessage : CoreCommon.getInstance().getPluginMessageManager().getPluginMessageQueue())
                CoreAPI.getInstance().getPluginMessageManager().sendPluginMessage(pluginMessage.getChannel(), pluginMessage.getSubChannel(), pluginMessage.getSenderName(), pluginMessage.getSenderType(), pluginMessage.getReceiverName(), pluginMessage.getMessage());
            CoreCommon.getInstance().getPluginMessageManager().getPluginMessageQueue().clear();
        }
    }
}
