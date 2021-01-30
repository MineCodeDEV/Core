package dev.minecode.core.bungeecord.listener;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.Type;
import dev.minecode.core.bungeecord.CoreBungeeCord;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.*;
import java.util.HashMap;

public class PluginMessageListener implements Listener {

    public PluginMessageListener() {
        Plugin plugin = CoreBungeeCord.getInstance().getMainClass();
        plugin.getProxy().getPluginManager().registerListener(plugin, new PluginMessageListener());
    }

    @EventHandler
    public void handlePluginMessage(PluginMessageEvent event) {
        if (!event.getTag().equals("MineCode")) return;

        ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(event.getData());
        DataInputStream dataInput = new DataInputStream(byteArrayInput);

        String channel;
        String subChannel;
        String senderName;
        String receiverName;
        HashMap<String, Object> message;

        try {
            channel = dataInput.readUTF();
            subChannel = dataInput.readUTF();
            senderName = dataInput.readUTF();
            receiverName = dataInput.readUTF();
            message = CoreCommon.getInstance().getPluginMessageManager().getMessageHashMap(dataInput);
            CoreAPI.getInstance().getPluginMessageManager().sendPluginMessage(channel, subChannel, senderName, receiverName, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
