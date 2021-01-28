package dev.minecode.core.bungeecord.listener;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.Type;
import dev.minecode.core.bungeecord.CoreBungeeCord;
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
        Type senderType;
        String receiverName;
        HashMap<String, Object> message;
        ObjectInputStream objectInput;

        try {
            channel = dataInput.readUTF();
            subChannel = dataInput.readUTF();
            senderName = dataInput.readUTF();
            senderType = Type.valueOf(dataInput.readUTF());
            receiverName = dataInput.readUTF();
            objectInput = new ObjectInputStream(dataInput);
            message = (HashMap<String, Object>) objectInput.readObject();
            CoreAPI.getInstance().getPluginMessageManager().sendPluginMessage(channel, subChannel, senderName, senderType, receiverName, message);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
