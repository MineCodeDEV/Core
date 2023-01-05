package dev.minecode.core.bungeecord.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.minecode.core.bungeecord.event.MineCodePluginMessageReceiveEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.lang.reflect.Type;
import java.util.HashMap;

public class BungeeCordListener implements Listener {

    private final Gson gson;
    private final Type type;

    public BungeeCordListener() {
        gson = new Gson();
        type = new TypeToken<HashMap<String, String>>() {
        }.getType();
    }

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent event) {
        if (!event.getTag().equalsIgnoreCase("minecode:pluginmessage")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        ProxyServer.getInstance().getPluginManager().callEvent(new MineCodePluginMessageReceiveEvent(in.readUTF(), in.readUTF(), gson.fromJson(in.readUTF(), type)));
    }

}
