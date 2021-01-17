package dev.minecode.core.bungeecord.listener;

import dev.minecode.core.api.CoreAPI;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.*;
import java.util.HashMap;

public class PluginMessageListener implements Listener {

    @EventHandler
    public void handlePluginMessage(PluginMessageEvent event) {
        if (!event.getTag().equals(CoreAPI.getInstance().getPluginMessageChannel())) return;

        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream input = new DataInputStream(stream);

        String receiver;
        String channel;
        HashMap<String, Object> data;

        try {
            receiver = input.readUTF();
            channel = input.readUTF();

            ObjectInputStream objectInput = new ObjectInputStream(input);
            data = (HashMap<String, Object>) objectInput.readObject();

            CoreAPI.getInstance().getPluginMessageManager().sendPluginMessage(receiver, channel, data);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
