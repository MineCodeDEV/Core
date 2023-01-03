package dev.minecode.core.spigot.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.NetworkManager;
import dev.minecode.core.api.manager.PluginMessageManager;
import dev.minecode.core.api.object.CloudPlattform;
import dev.minecode.core.api.object.QueuedPluginMessage;
import dev.minecode.core.common.api.object.QueuedPluginMessageProvider;
import dev.minecode.core.spigot.CoreSpigot;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PluginMessageManagerProvider implements PluginMessageManager {

    private final NetworkManager networkManager;
    private final Gson gson;

    private final List<QueuedPluginMessage> queuedPluginMessages;

    public PluginMessageManagerProvider() {
        gson = new Gson();
        networkManager = CoreAPI.getInstance().getNetworkManager();
        queuedPluginMessages = new ArrayList<>();
    }

    @Override
    public boolean sendPluginMessage(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message) {
        if (!networkManager.isEnabled()) return false;

        if (networkManager.getCloudPlattform() == CloudPlattform.NONE) {
            if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL())
                return sendPluginMessageOverSQL(targetServer, channel, message);
            if (!networkManager.isMultiproxy())
                return sendPluginMessageOverChannel(targetServer, channel, message);
        }

        if (networkManager.getCloudPlattform() == CloudPlattform.CLOUDNET)
            return sendPluginMessageOverCloudNet(targetServer, channel, message);

        if (networkManager.getCloudPlattform() == CloudPlattform.SIMPLECLOUD)
            return sendPluginMessageOverSimpleCloud(targetServer, channel, message);

        if (networkManager.getCloudPlattform() == CloudPlattform.TIMOCLOUD)
            return sendPluginMessageOverTimoCloud(targetServer, channel, message);

        return false;
    }

    @Override
    public void executeQueue() {
        for (int i = 0; i < queuedPluginMessages.size(); i++)
            queuedPluginMessages.get(i).sendPluginMessage();
    }

    private boolean sendPluginMessageOverChannel(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message) {
        if (Bukkit.getOnlinePlayers().size() == 0) {
            addPluginMessageToQueue(new QueuedPluginMessageProvider(targetServer, channel, message));
            return false;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        String messageJson = gson.toJson(message);
        if (targetServer.equals("Proxy")) {
            out.writeUTF(channel);
            out.writeUTF(CoreAPI.getInstance().getNetworkManager().getServername());
            out.writeUTF(messageJson);
            Bukkit.getServer().sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "minecode:pluginmessage", out.toByteArray());
            return true;
        }

        out.writeUTF("Forward");
        out.writeUTF(targetServer);
        out.writeUTF("minecode:pluginmessage");

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);

        try {
            msgout.writeUTF(channel);
            msgout.writeUTF(CoreAPI.getInstance().getNetworkManager().getServername());
            msgout.writeUTF(messageJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());

        Bukkit.getServer().sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", out.toByteArray());
        return true;
    }

    private boolean sendPluginMessageOverSQL(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message) {
        return false;
    }

    private boolean sendPluginMessageOverCloudNet(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message) {
        return false;
    }

    private boolean sendPluginMessageOverSimpleCloud(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message) {
        return false;
    }

    private boolean sendPluginMessageOverTimoCloud(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message) {
        return false;
    }

    public boolean addPluginMessageToQueue(QueuedPluginMessage queuedPluginMessage) {
        return queuedPluginMessages.add(queuedPluginMessage);
    }

    @Override
    public List<QueuedPluginMessage> getQueuedPluginMessages() {
        return new ArrayList<>(queuedPluginMessages);
    }
}
