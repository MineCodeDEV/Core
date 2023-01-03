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
        Bukkit.getLogger().info("PM received");


        Bukkit.getLogger().info("1");
        if (bukkitChannel.equals("BungeeCord")) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            String subChannel = null;
            try {
                subChannel = in.readUTF();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//      Wenn PluginMessage von anderem Server über BungeeCord zu diesem Server weitergeleitet wird:
            Bukkit.getLogger().info("2");
            if (subChannel.equals("minecode:pluginmessage")) {
                String channel = null;
                try {
                    channel = in.readUTF();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
//                Bukkit.getLogger().info("channel: " + channel);
                String finalChannel = channel;
                Bukkit.getOnlinePlayers().forEach(player1 -> player1.sendMessage("channel: " + finalChannel));
                String sender = null;
                try {
                    sender = in.readUTF();
                } catch (IOException e) {
                    Bukkit.getLogger().info("hier wäre jz n dicker Fehler");
                }
                Bukkit.getLogger().info("sender: " + sender);
//                HashMap<String, String> message = gson.fromJson(in.readUTF(), type);
//                Bukkit.getPluginManager().callEvent(new MineCodePluginMessageReceiveEvent(channel,
//                        sender,
//                        message));
//                Bukkit.getLogger().info("3");
                return;
            }

            if (subChannel.equals("GetServer")) {
                Bukkit.getLogger().info("4");
                try {
                    CoreAPI.getInstance().getNetworkManager().setServername(in.readUTF());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (!CoreAPI.getInstance().getPluginMessageManager().getQueuedPluginMessages().isEmpty())
                    CoreAPI.getInstance().getPluginMessageManager().executeQueue();
            }
            return;
        }

//        Wenn PluginMessage von BungeeCord zu diesem Server gesendet wird
        if (bukkitChannel.equals("minecode:pluginmessage")) {
            Bukkit.getLogger().info("5");
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            Bukkit.getPluginManager().callEvent(new MineCodePluginMessageReceiveEvent(in.readUTF(), in.readUTF(), gson.fromJson(in.readUTF(), type)));
            return;
        }
        Bukkit.getLogger().info("6");
    }
}
