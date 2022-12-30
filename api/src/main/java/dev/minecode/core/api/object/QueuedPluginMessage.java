package dev.minecode.core.api.object;

import java.util.HashMap;

public interface QueuedPluginMessage {

    void sendPluginMessage();

    String getTargetServer();

    String getChannel();

    HashMap<String, String> getMessage();

}
