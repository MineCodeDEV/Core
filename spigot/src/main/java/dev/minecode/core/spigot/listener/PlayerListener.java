package dev.minecode.core.spigot.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.spigot.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void handlePlayerRegisterChannel(PlayerRegisterChannelEvent event) {
        if (CoreAPI.getInstance().getNetworkManager().isServernameSet()) {
            if (!CoreAPI.getInstance().getPluginMessageManager().getQueuedPluginMessages().isEmpty())
                CoreAPI.getInstance().getPluginMessageManager().executeQueue();
        } else {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("GetServer");
            Bukkit.getServer().sendPluginMessage(CoreSpigot.getInstance(), "BungeeCord", out.toByteArray());
        }
    }
}
