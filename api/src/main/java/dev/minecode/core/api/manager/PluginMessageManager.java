package dev.minecode.core.api.manager;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public interface PluginMessageManager {

    boolean sendPluginMessage(@NotNull String targetServer, @NotNull String channel, @NotNull HashMap<String, String> message, boolean queue);

}
