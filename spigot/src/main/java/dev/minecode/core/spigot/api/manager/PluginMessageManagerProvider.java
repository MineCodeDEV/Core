package dev.minecode.core.spigot.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.event.PluginMessageEvent;
import dev.minecode.core.api.manager.PluginMessageManager;
import dev.minecode.core.api.object.Type;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.common.object.PluginMessage;
import dev.minecode.core.spigot.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class PluginMessageManagerProvider implements PluginMessageManager {
    @Override
    public void sendPluginMessage(String channel, String subChannel, String senderName, Type senderType, String receiverName, HashMap<String, Object> message) {
        ByteArrayOutputStream byteArrayOutput = CoreCommon.getInstance().getPluginMessageManager().getByteArrayOutputStream(channel, subChannel, senderName, senderType, receiverName, message);

        if (receiverName.equals(CoreAPI.getInstance().getProcessName())) {
            CoreAPI.getInstance().getEventManager().callEvent(new PluginMessageEvent(channel, subChannel, senderName, senderType, receiverName, message));
            return;
        }

        // sql is enabled
        if (CoreAPI.getInstance().isUsingSQL()) {
            try {
                CoreAPI.getInstance().getDatabaseManager().getStatement().execute("INSERT INTO minecode_messaging (CHANNEL, SUBCHANNEL, SENDERNAME, SENDERTYPE, RECEIVERNAME, MESSAGE) VALUES ('" + channel + "','" + subChannel + "', '" + senderName + "', '" + senderType.toString() + "', '" + receiverName + "', '" + Arrays.toString(byteArrayOutput.toByteArray()) + "')");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return;
        }

        // sql is disabled and no player is online
        if (Bukkit.getOnlinePlayers().size() == 0) {
            PluginMessage pluginMessage = new PluginMessage(channel, subChannel, senderName, senderType, receiverName, message);
            CoreCommon.getInstance().getPluginMessageManager().getPluginMessageQueue().add(pluginMessage);
            return;
        }

        // sql is disabled and min. one player is online
        Player player = Bukkit.getOnlinePlayers().iterator().next();
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "MineCode", byteArrayOutput.toByteArray());
    }

    @Override
    public void sendPluginMessage(String channel, String subChannel, String receiverName, HashMap<String, Object> message) {
        sendPluginMessage(channel, subChannel, CoreAPI.getInstance().getProcessName(), CoreAPI.getInstance().getProcessType(), receiverName, message);
    }
}
