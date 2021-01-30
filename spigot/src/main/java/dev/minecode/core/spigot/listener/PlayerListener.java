package dev.minecode.core.spigot.listener;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.common.object.PluginMessage;
import dev.minecode.core.spigot.CoreSpigot;
import dev.minecode.core.spigot.util.BungeeCordPluginMessage;
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
            Bukkit.getScheduler().runTaskLater(CoreSpigot.getInstance().getMainClass(), new Runnable() {
                @Override
                public void run() {
                    if (CoreCommon.getInstance().getProcessName() == null) {
                        BungeeCordPluginMessage.getServer(event.getPlayer().getName());
                    }

                    Bukkit.getScheduler().runTaskLater(CoreSpigot.getInstance().getMainClass(), new Runnable() {
                        @Override
                        public void run() {
                            for (PluginMessage pluginMessage : CoreCommon.getInstance().getPluginMessageManager().getPluginMessageQueue())
                                CoreAPI.getInstance().getPluginMessageManager().sendPluginMessage(pluginMessage.getChannel(), pluginMessage.getSubChannel(), pluginMessage.getSenderName(), pluginMessage.getReceiverName(), pluginMessage.getMessage());
                            CoreCommon.getInstance().getPluginMessageManager().getPluginMessageQueue().clear();
                        }
                    }, 20);
                }
            }, 20);
        }
    }

}
