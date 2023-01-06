package dev.minecode.core.common.api.cloudsupport;


public class SimpleCloudPluginMessage {

    private final String channel;
    private final String messageToJson;

    public SimpleCloudPluginMessage(String channel, String messageToJson) {
        this.channel = channel;
        this.messageToJson = messageToJson;
    }

    public String getChannel() {
        return channel;
    }

    public String getMessageToJson() {
        return messageToJson;
    }
}