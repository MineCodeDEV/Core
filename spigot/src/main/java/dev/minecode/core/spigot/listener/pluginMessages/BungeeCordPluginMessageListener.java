package dev.minecode.core.spigot.listener.pluginMessages;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.CoreSpigot;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class BungeeCordPluginMessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(bytes);
        DataInputStream dataInput = new DataInputStream(byteArrayInput);

        try {
            String subChannel = dataInput.readUTF();

            if (subChannel.equals("MineCode:Forward")) {
                short lenth = dataInput.readShort();
                byte[] msgbytes = new byte[lenth];
                dataInput.readFully(msgbytes);

                DataInputStream dataInputForward = new DataInputStream(byteArrayInput);

                String channel;
                String subChannelForward;
                String senderName;
                String receiverName;
                HashMap<String, Object> message;

                try {
                    channel = dataInputForward.readUTF();
                    subChannelForward = dataInputForward.readUTF();
                    senderName = dataInputForward.readUTF();
                    receiverName = dataInputForward.readUTF();
                    message = CoreCommon.getInstance().getPluginMessageManager().getMessageHashMap(dataInputForward);
                    CoreAPI.getInstance().getPluginMessageManager().sendPluginMessage(channel, subChannelForward, senderName, receiverName, message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (subChannel.equals("GetServer")) {
                String serverName = dataInput.readUTF();

                CoreCommon.getInstance().setProcessName(serverName);
                return;
            }

            if (subChannel.equals("GetServers")) {
                String[] serverList = dataInput.readUTF().split(", ");

                CoreSpigot.getInstance().getPluginMessageManagerProvider().getServers().clear();
                for (String server : serverList)
                    CoreSpigot.getInstance().getPluginMessageManagerProvider().getServers().add(server);
                return;
            }

            if (subChannel.equals("AddServer")) {
                String server = dataInput.readUTF();

                CoreSpigot.getInstance().getPluginMessageManagerProvider().getServers().add(server);
                return;
            }

            if (subChannel.equals("RemoveServer")) {
                String server = dataInput.readUTF();

                CoreSpigot.getInstance().getPluginMessageManagerProvider().getServers().remove(server);
                return;
            }

            if (subChannel.equals("ServerIP")) {
                String serverName = dataInput.readUTF();
                String ip = dataInput.readUTF();
                int port = dataInput.readUnsignedShort();

                if (serverName.equals(CoreAPI.getInstance().getProcessName()))
                CoreCommon.getInstance().setProcessName(serverName);
                CoreCommon.getInstance().setProcessIp(ip);
                CoreCommon.getInstance().setProcessPort(port);
            }

            if (subChannel.equals("UUID")) {
                String uuid = dataInput.readUTF();

                CoreAPI.getInstance().getCorePlayer(player.getUniqueId()).setUuid(UUID.fromString(uuid));
                return;
            }

            if (subChannel.equals("UUIDOther")) {
                String uuid = dataInput.readUTF();
                String name = dataInput.readUTF();

                CoreAPI.getInstance().getCorePlayer(name).setUuid(UUID.fromString(uuid));
                return;
            }

            if (subChannel.equals("IP")) {
                String ip = dataInput.readUTF();
                int port = dataInput.readInt();

                //CoreAPI.getInstance().getCorePlayer(player.getUniqueId()).setIp(ip);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
