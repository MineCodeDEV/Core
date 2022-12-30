package dev.minecode.core.bungeecord.event;

import net.md_5.bungee.api.plugin.Event;

import java.util.HashMap;

public class MineCodePluginMessageReceiveEvent extends Event {

    private final String channel, sender;
    private final HashMap<String, String> message;

    public MineCodePluginMessageReceiveEvent(String channel, String sender, HashMap<String, String> message) {
        this.channel = channel;
        this.sender = sender;
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public String getSender() {
        return sender;
    }

    public HashMap<String, String> getMessage() {
        return message;
    }

}
