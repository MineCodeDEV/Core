package dev.minecode.core.bungeecord.api.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.NetworkManager;
import dev.minecode.core.api.manager.PluginMessageManager;
import dev.minecode.core.api.object.CloudPlattform;
import dev.minecode.core.api.object.QueuedPluginMessage;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class PluginMessageManagerProvider implements PluginMessageManager {

    private final NetworkManager networkManager;
    private final Gson gson;

    public PluginMessageManagerProvider() {
        gson = new Gson();
        networkManager = CoreAPI.getInstance().getNetworkManager();
    }

    @Override
    public boolean sendPluginMessage(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message) {
        if (!networkManager.isEnabled()) return false;

        if (networkManager.getCloudPlattform() == CloudPlattform.NONE)
            if (networkManager.isMultiproxy()) return sendPluginMessageOverSQL(targetServer, channel, message);
            else return sendPluginMessageOverChannel(targetServer, channel, message);

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
    }

    @Override
    public List<QueuedPluginMessage> getQueuedPluginMessages() {
        return null;
    }

    private boolean sendPluginMessageOverChannel(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message) {
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(targetServer);
        if (serverInfo == null) return false;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(channel);
        out.writeUTF(CoreAPI.getInstance().getNetworkManager().getServername());
        out.writeUTF(gson.toJson(message));

        return serverInfo.sendData("minecode:pluginmessage", out.toByteArray(), true);
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

}
