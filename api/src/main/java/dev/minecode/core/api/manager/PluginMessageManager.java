package dev.minecode.core.api.manager;

import java.util.HashMap;

public interface PluginMessageManager {

    void sendPluginMessage(String channel, String subChannel, String senderName, String receiverName, HashMap<String, Object> message);

    void sendPluginMessage(String channel, String subChannel, String receiverName, HashMap<String, Object> message);

}
