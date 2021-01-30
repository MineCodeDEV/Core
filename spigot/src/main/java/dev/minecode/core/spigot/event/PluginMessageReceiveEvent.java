package dev.minecode.core.spigot.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;

public class PluginMessageReceiveEvent extends Event {

    String channel;
    String subChannel;
    String senderName;
    HashMap<String, Object> message;

    private static HandlerList handlers = new HandlerList();

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

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
