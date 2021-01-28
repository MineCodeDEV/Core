package dev.minecode.core.bungeecord.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.event.PluginMessageEvent;
import dev.minecode.core.api.manager.PluginMessageManager;
import dev.minecode.core.api.object.Type;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

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

        // it's a server
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(receiverName);
        if (serverInfo != null) {
            serverInfo.sendData("MineCode", byteArrayOutput.toByteArray());
            return;
        }

        /* Multi-Proxy
        // it's a proxy and sql is enabled
        if (CoreAPI.getInstance().isUsingSQL()) {
            try {
                CoreAPI.getInstance().getDatabaseManager().getStatement().execute("INSERT INTO minecode_messaging (CHANNEL, SUBCHANNEL, SENDERNAME, SENDERTYPE, RECEIVERNAME, MESSAGE) VALUES ('" + channel + "','" + subChannel + "', '" + senderName + "', '" + senderType.toString() + "', '" + receiverName + "', '" + Arrays.toString(byteArrayOutput.toByteArray()) + "')");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return;
        }

        // it's a proxy and sql is disabled
        //TODO: Forward over a server to a BungeeCord
        */
    }

    @Override
    public void sendPluginMessage(String channel, String subChannel, String receiverName, HashMap<String, Object> message) {
        sendPluginMessage(channel, subChannel, CoreAPI.getInstance().getProcessName(), CoreAPI.getInstance().getProcessType(), receiverName, message);
    }
}
