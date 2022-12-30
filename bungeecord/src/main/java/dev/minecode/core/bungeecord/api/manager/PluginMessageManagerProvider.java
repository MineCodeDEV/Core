package dev.minecode.core.bungeecord.api.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.NetworkManager;
import dev.minecode.core.api.manager.PluginMessageManager;
import dev.minecode.core.api.object.CloudPlattform;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PluginMessageManagerProvider implements PluginMessageManager {

    private final NetworkManager networkManager;
    private final Gson gson;

    public PluginMessageManagerProvider() {
        gson = new Gson();
        networkManager = CoreAPI.getInstance().getNetworkManager();
    }

    @Override
    public boolean sendPluginMessage(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message, boolean queue) {
        if (!networkManager.isEnabled()) return false;

        if (networkManager.getCloudPlattform() == CloudPlattform.NONE)
            if (networkManager.isMultiproxy()) return sendPluginMessageOverSQL(targetServer, channel, message, queue);
            else return sendPluginMessageOverChannel(targetServer, channel, message, queue);

        if (networkManager.getCloudPlattform() == CloudPlattform.CLOUDNET)
            return sendPluginMessageOverCloudNet(targetServer, channel, message, queue);

        if (networkManager.getCloudPlattform() == CloudPlattform.SIMPLECLOUD)
            return sendPluginMessageOverSimpleCloud(targetServer, channel, message, queue);

        if (networkManager.getCloudPlattform() == CloudPlattform.TIMOCLOUD)
            return sendPluginMessageOverTimoCloud(targetServer, channel, message, queue);

        return false;
    }

    private boolean sendPluginMessageOverChannel(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message, boolean queue) {
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(targetServer);
        if (serverInfo == null) return false;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(channel);
        out.writeUTF("Proxy");
        out.writeUTF(gson.toJson(message));

        boolean success = serverInfo.sendData("minecode:pluginmessage", out.toByteArray(), queue);

        return queue || success;
    }

    private boolean sendPluginMessageOverSQL(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message, boolean queue) {
        return false;
    }

    private boolean sendPluginMessageOverCloudNet(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message, boolean queue) {
        return false;
    }

    private boolean sendPluginMessageOverSimpleCloud(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message, boolean queue) {
        return false;
    }

    private boolean sendPluginMessageOverTimoCloud(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message, boolean queue) {
        return false;
    }

}