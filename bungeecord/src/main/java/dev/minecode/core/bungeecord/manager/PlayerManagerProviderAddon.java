package dev.minecode.core.bungeecord.manager;

import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.bungeecord.api.object.CorePlayerProvider;
import dev.minecode.core.common.api.manager.PlayerManagerProvider;

import java.util.UUID;

public class PlayerManagerProviderAddon extends PlayerManagerProvider {
    @Override
    public CorePlayer newCorePlayer(int id) {
        return new CorePlayerProvider(id);
    }

    @Override
    public CorePlayer newCorePlayer(UUID uuid) {
        return new CorePlayerProvider(uuid);
    }

    @Override
    public CorePlayer newCorePlayer(String name) {
        return new CorePlayerProvider(name);
    }
}
