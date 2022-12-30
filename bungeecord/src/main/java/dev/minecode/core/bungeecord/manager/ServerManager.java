package dev.minecode.core.bungeecord.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class ServerManager {

    public void sendServerNames() {
        for (ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) sendServerName(serverInfo);
    }

    public void sendServerName(ServerInfo serverInfo) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServer");
        out.writeUTF(serverInfo.getName());
        serverInfo.sendData("minecode:intern", out.toByteArray(), true);
    }

}
