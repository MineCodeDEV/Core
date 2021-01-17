package dev.minecode.core.api.event;

import dev.minecode.core.api.object.CoreEvent;

import java.util.HashMap;

public class PluginMessageEvent implements CoreEvent {

    private String receiver, channel;
    private HashMap<String, Object> data;

    public PluginMessageEvent(String receiver, String channel, HashMap<String, Object> data) {
        this.receiver = receiver;
        this.channel = channel;
        this.data = data;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getChannel() {
        return channel;
    }

    public HashMap<String, Object> getData() {
        return data;
    }
}
