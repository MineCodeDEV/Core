package dev.minecode.core.spigot.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.NetworkManager;
import dev.minecode.core.api.manager.PluginMessageManager;
import dev.minecode.core.api.object.CloudPlattform;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        // TODO: queue einbauen, falls kein Spieler online ist

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        String messageJson = gson.toJson(message);
        if (targetServer.equals("Proxy")) {
            out.writeUTF(channel);
            out.writeUTF("server"); //TODO: richtigen Servernamen holen
            out.writeUTF(messageJson);
            Bukkit.getServer().sendPluginMessage(((JavaPlugin) CoreAPI.getInstance().getPluginManager().getPlugins().get(0)), "minecode:pluginmessage", out.toByteArray());
            return true;
        }

        out.writeUTF("Forward");
        out.writeUTF(targetServer);
        out.writeUTF(channel);

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);

        try {
            msgout.writeUTF(messageJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());

        Bukkit.getServer().sendPluginMessage(((JavaPlugin) CoreAPI.getInstance().getPluginManager().getPlugins().get(0)), "BungeeCord", out.toByteArray());
        return true;
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
