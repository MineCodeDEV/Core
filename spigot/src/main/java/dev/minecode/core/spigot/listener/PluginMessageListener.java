package dev.minecode.core.spigot.listener;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.Type;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (!s.equals("MineCode")) return;

        ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(bytes);
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
