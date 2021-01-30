package dev.minecode.core.bungeecord.event;

import net.md_5.bungee.api.plugin.Event;

import java.util.HashMap;

public class PluginMessageReceiveEvent extends Event {

    String channel;
    String subChannel;
    String senderName;
    HashMap<String, Object> message;

    public PluginMessageReceiveEvent(String channel, String subChannel, String senderName, HashMap<String, Object> message) {
        this.channel = channel;
        this.subChannel = subChannel;
        this.senderName = senderName;
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public String getSubChannel() {
        return subChannel;
    }

    public String getSenderName() {
        return senderName;
    }

    public HashMap<String, Object> getMessage() {
        return message;
    }
}
