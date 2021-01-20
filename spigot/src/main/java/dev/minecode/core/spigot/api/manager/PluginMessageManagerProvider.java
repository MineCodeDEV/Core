package dev.minecode.core.spigot.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.PluginMessageManager;
import dev.minecode.core.spigot.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class PluginMessageManagerProvider implements PluginMessageManager {
    @Override
    public boolean sendPluginMessage(String receiver, String channel, HashMap<String, Object> data) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(stream);
        ObjectOutputStream objectOutput;
        try {
            output.writeUTF(receiver);
            output.writeUTF(channel);
            objectOutput = new ObjectOutputStream(output);
            objectOutput.writeObject(data);
            objectOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getOnlinePlayers().iterator().next();
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), CoreAPI.getInstance().getPluginMessageChannel(), stream.toByteArray());
        return false;
    }
}
