package dev.minecode.core.spigot.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MineCodePluginMessageReceiveEvent extends Event {

    private static final HandlerList handlerlist = new HandlerList();

    private final String channel, sender;
    private final HashMap<String, String> message;

    public MineCodePluginMessageReceiveEvent(String channel, String sender, HashMap<String, String> message) {
        this.channel = channel;
        this.sender = sender;
        this.message = message;
    }

    public static HandlerList getHandlerList() {
        return handlerlist;
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

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerlist;
    }
}
