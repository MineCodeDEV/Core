package dev.minecode.core.bungeecord.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.event.PluginMessageEvent;
import dev.minecode.core.api.manager.PluginMessageManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.*;
import java.util.HashMap;

public class PluginMessageManagerProvider implements PluginMessageManager {
    @Override
    public boolean sendPluginMessage(String receiver, String channel, HashMap<String, Object> data) {
        if (receiver.equalsIgnoreCase("BungeeCord")) {
            CoreAPI.getInstance().getEventManager().callEvent(new PluginMessageEvent(receiver, channel, data));
            return true;
        }

        ServerInfo serverInfo;
        if ((serverInfo = ProxyServer.getInstance().getServerInfo(receiver)) != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream output = new DataOutputStream(stream);
            ObjectOutputStream objectOutput;

            try {
                output.writeUTF(channel);
                objectOutput = new ObjectOutputStream(output);
                objectOutput.writeObject(data);
                objectOutput.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            serverInfo.sendData(CoreAPI.getInstance().getPluginMessageChannel(), stream.toByteArray());
            return true;
        }
        return false;
    }
}
