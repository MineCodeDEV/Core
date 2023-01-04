package dev.minecode.core.api.manager;

import java.util.HashMap;

public interface SQLPluginMessageManager {

    void startChecking();

    boolean cancelChecking();

    void checkForPluginMessages();

    void sendPluginMessage(String targetServer, String channel, HashMap<String, String> message);

    void setInterval(int interval);

}
