package dev.minecode.core.spigot.listener.pluginMessages;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.Type;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class MineCodePluginMessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(bytes);
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
