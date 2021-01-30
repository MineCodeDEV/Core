package dev.minecode.core.spigot.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.PluginMessageManager;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.common.object.PluginMessage;
import dev.minecode.core.spigot.CoreSpigot;
import dev.minecode.core.spigot.event.PluginMessageReceiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PluginMessageManagerProvider implements PluginMessageManager {

    private ArrayList<String> servers = new ArrayList<>();

    @Override
    public void sendPluginMessage(String channel, String subChannel, String senderName, String receiverName, HashMap<String, Object> message) {
        ByteArrayOutputStream byteArrayOutput = CoreCommon.getInstance().getPluginMessageManager().getByteArrayOutputStream(channel, subChannel, senderName, receiverName, message);

        if (receiverName.equals(CoreAPI.getInstance().getProcessName())) {
            Bukkit.getPluginManager().callEvent(new PluginMessageReceiveEvent(channel, subChannel, senderName, message));
            return;
        }

        // sql is disabled and min. one player is online
        if (Bukkit.getOnlinePlayers().size() != 0) {
            Player player = Bukkit.getOnlinePlayers().iterator().next();
            player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "MineCode", byteArrayOutput.toByteArray());
            return;
        }

        // sql is enabled
        if (CoreAPI.getInstance().isUsingSQL()) {
            try {
                CoreAPI.getInstance().getDatabaseManager().getStatement().execute("INSERT INTO minecode_messaging (CHANNEL, SUBCHANNEL, SENDERNAME, RECEIVERNAME, MESSAGE) VALUES ('" + channel + "','" + subChannel + "', '" + senderName + "', '" + receiverName + "', '" + Arrays.toString(byteArrayOutput.toByteArray()) + "')");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return;
        }

        // sql is disabled and no player is online
        PluginMessage pluginMessage = new PluginMessage(channel, subChannel, senderName, receiverName, message);
        CoreCommon.getInstance().getPluginMessageManager().getPluginMessageQueue().add(pluginMessage);
    }

    @Override
    public void sendPluginMessage(String channel, String subChannel, String receiverName, HashMap<String, Object> message) {
        sendPluginMessage(channel, subChannel, CoreAPI.getInstance().getProcessName(), receiverName, message);
    }

    public ArrayList<String> getServers() {
        return servers;
    }

    public void setServers(ArrayList<String> servers) {
        this.servers = servers;
    }
}
