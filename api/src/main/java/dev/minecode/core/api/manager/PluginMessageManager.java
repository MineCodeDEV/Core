package dev.minecode.core.api.manager;

import java.util.HashMap;

public interface PluginMessageManager {

    boolean sendPluginMessage(String receiver, String channel, HashMap<String, Object> data);

}
