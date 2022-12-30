package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.QueuedPluginMessage;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public interface PluginMessageManager {

    boolean sendPluginMessage(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message, boolean queue);

    void executeQueue();

    List<QueuedPluginMessage> getQueuedPluginMessages();
}
