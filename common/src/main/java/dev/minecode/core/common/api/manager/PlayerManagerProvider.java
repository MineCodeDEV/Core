package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.manager.PlayerManager;
import dev.minecode.core.api.object.CorePlayer;

import java.util.UUID;

public abstract class PlayerManagerProvider implements PlayerManager {
    @Override
    public CorePlayer getCorePlayer(int id) {
        CorePlayer corePlayer = newCorePlayer(id);
        if (corePlayer.isExists()) {
            return corePlayer;
        }
        return null;
    }

    @Override
    public CorePlayer getCorePlayer(UUID uuid) {
        CorePlayer corePlayer = newCorePlayer(uuid);
        if (corePlayer.isExists()) {
            return corePlayer;
        }
        return null;
    }

    @Override
    public CorePlayer getCorePlayer(String name) {
        CorePlayer corePlayer = newCorePlayer(name);
        if (corePlayer.isExists()) {
            return corePlayer;
        }
        return null;
    }

    public abstract CorePlayer newCorePlayer(int id);

    public abstract CorePlayer newCorePlayer(UUID uuid);

    public abstract CorePlayer newCorePlayer(String name);
}
