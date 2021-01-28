package dev.minecode.core.common.object;

import dev.minecode.core.api.object.Type;

import java.util.HashMap;

public class PluginMessage {

    private String channel;
    private String subChannel;
    private String senderName;
    private Type senderType;
    private String receiverName;
    private HashMap<String, Object> message;

    public PluginMessage(String channel, String subChannel, String senderName, Type senderType, String receiverName, HashMap<String, Object> message) {
        this.channel = channel;
        this.subChannel = subChannel;
        this.senderName = senderName;
        this.senderType = senderType;
        this.receiverName = receiverName;
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

    public Type getSenderType() {
        return senderType;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public HashMap<String, Object> getMessage() {
        return message;
    }
}
