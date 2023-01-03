package dev.minecode.core.common.api.object;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.QueuedPluginMessage;

import java.util.HashMap;

public class QueuedPluginMessageProvider implements QueuedPluginMessage {

    private final String targetServer;
    private final String channel;
    private final HashMap<String, String> message;

    public QueuedPluginMessageProvider(String targetServer, String channel, HashMap<String, String> message) {
        this.targetServer = targetServer;
        this.channel = channel;
        this.message = message;
    }

    @Override
    public void sendPluginMessage() {
        CoreAPI.getInstance().getPluginMessageManager().sendPluginMessage(targetServer, channel, message);
    }

    @Override
    public String getTargetServer() {
        return targetServer;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public HashMap<String, String> getMessage() {
        return message;
    }
}
