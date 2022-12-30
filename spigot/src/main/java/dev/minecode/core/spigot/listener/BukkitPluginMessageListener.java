package dev.minecode.core.spigot.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.spigot.event.MineCodePluginMessageReceiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.HashMap;

public class BukkitPluginMessageListener implements PluginMessageListener {

    private final Gson gson;
    private final Type type;

    public BukkitPluginMessageListener() {
        gson = new Gson();
        type = new TypeToken<HashMap<String, String>>() {
        }.getType();
    }

    @Override
    public void onPluginMessageReceived(@NotNull String bukkitChannel, @NotNull Player player, @NotNull byte[] bytes) {
        if (bukkitChannel.equals("minecode:pluginmessage")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            Bukkit.getPluginManager().callEvent(new MineCodePluginMessageReceiveEvent(in.readUTF(), in.readUTF(), gson.fromJson(in.readUTF(), type)));
            return;
        }

        if (bukkitChannel.equals("BungeeCord")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String channel = in.readUTF();

            if (channel.equals("GetServer")) {
                CoreAPI.getInstance().getNetworkManager().setServername(in.readUTF());
                if (!CoreAPI.getInstance().getPluginMessageManager().getQueuedPluginMessages().isEmpty())
                    CoreAPI.getInstance().getPluginMessageManager().executeQueue();
            }
        }
    }
}
