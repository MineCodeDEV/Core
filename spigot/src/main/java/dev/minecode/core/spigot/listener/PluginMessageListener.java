package dev.minecode.core.spigot.listener;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.event.PluginMessageEvent;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (!s.equals(CoreAPI.getInstance().getPluginMessageChannel())) return;

        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        DataInputStream input = new DataInputStream(stream);

        String receiver;
        String channel;
        HashMap<String, Object> data;
        ObjectInputStream objectInput;

        try {
            receiver = input.readUTF();
            channel = input.readUTF();
            objectInput = new ObjectInputStream(input);
            data = (HashMap<String, Object>) objectInput.readObject();
            CoreAPI.getInstance().getEventManager().callEvent(new PluginMessageEvent(receiver, channel, data));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
