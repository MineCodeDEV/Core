package dev.minecode.core.bungeecord.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.PluginMessageManager;
import dev.minecode.core.bungeecord.event.PluginMessageReceiveEvent;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.*;
import java.util.HashMap;

public class PluginMessageManagerProvider implements PluginMessageManager {
    @Override
    public void sendPluginMessage(String channel, String subChannel, String senderName, String receiverName, HashMap<String, Object> message) {
        ByteArrayOutputStream byteArrayOutput = CoreCommon.getInstance().getPluginMessageManager().getByteArrayOutputStream(channel, subChannel, senderName, receiverName, message);

        if (receiverName.equals(CoreAPI.getInstance().getProcessName())) {
            ProxyServer.getInstance().getPluginManager().callEvent(new PluginMessageReceiveEvent(channel, subChannel, senderName, message));
            return;
        }

        // it's a server
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(receiverName);
        if (serverInfo != null) {
            serverInfo.sendData("MineCode", byteArrayOutput.toByteArray());
        }
    }

    @Override
    public void sendPluginMessage(String channel, String subChannel, String receiverName, HashMap<String, Object> message) {
        sendPluginMessage(channel, subChannel, CoreAPI.getInstance().getProcessName(), receiverName, message);
    }
}
