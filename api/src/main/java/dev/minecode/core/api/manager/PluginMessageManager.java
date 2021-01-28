package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.Type;

import java.util.HashMap;

public interface PluginMessageManager {

    void sendPluginMessage(String channel, String subChannel, String senderName, Type senderType, String receiverName, HashMap<String, Object> message);

    void sendPluginMessage(String channel, String subChannel, String receiverName, HashMap<String, Object> message);

}
