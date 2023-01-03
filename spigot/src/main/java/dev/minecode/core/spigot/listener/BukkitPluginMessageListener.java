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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
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
    public void onPluginMessageReceived(@NotNull String bukkitChannel, @NotNull Player player, @NotNull byte[] message) {
        if (bukkitChannel.equals("BungeeCord")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subChannel = in.readUTF();

//      Wenn PluginMessage von anderem Server über BungeeCord zu diesem Server weitergeleitet wird:
            if (subChannel.equals("minecode:pluginmessage")) {

                short len = in.readShort();
                byte[] msgbytes = new byte[len];
                in.readFully(msgbytes);

                DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));

                String channel = null;
                String servername = null;
                String messageJson = null;
                try {
                    channel = msgin.readUTF();
                    servername = msgin.readUTF();
                    messageJson = msgin.readUTF();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                HashMap<String, String> hashmapMessage = gson.fromJson(messageJson, type);
                Bukkit.getPluginManager().callEvent(new MineCodePluginMessageReceiveEvent(channel, servername, hashmapMessage));

                return;
            }

            if (subChannel.equals("GetServer")) {
                CoreAPI.getInstance().getNetworkManager().setServername(in.readUTF());
                if (!CoreAPI.getInstance().getPluginMessageManager().getQueuedPluginMessages().isEmpty())
                    CoreAPI.getInstance().getPluginMessageManager().executeQueue();
            }
            return;
        }

//        Wenn PluginMessage von BungeeCord zu diesem Server gesendet wird
        if (bukkitChannel.equals("minecode:pluginmessage")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            Bukkit.getPluginManager().callEvent(new MineCodePluginMessageReceiveEvent(in.readUTF(), in.readUTF(), gson.fromJson(in.readUTF(), type)));
        }
    }
}
