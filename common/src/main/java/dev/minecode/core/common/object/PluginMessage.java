package dev.minecode.core.common.object;

import java.util.HashMap;

public class PluginMessage {

    private String channel;
    private String subChannel;
    private String senderName;
    private String receiverName;
    private HashMap<String, Object> message;

    public PluginMessage(String channel, String subChannel, String senderName, String receiverName, HashMap<String, Object> message) {
        this.channel = channel;
        this.subChannel = subChannel;
        this.senderName = senderName;
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

    public String getReceiverName() {
        return receiverName;
    }

    public HashMap<String, Object> getMessage() {
        return message;
    }
}
