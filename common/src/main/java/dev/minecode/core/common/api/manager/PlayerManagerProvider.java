package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.manager.PlayerManager;
import dev.minecode.core.api.object.CorePlayer;

import java.util.UUID;

public abstract class PlayerManagerProvider implements PlayerManager {

    @Override
    public CorePlayer getPlayer(UUID uuid) {
        CorePlayer corePlayer = newPlayer(uuid);
        if (corePlayer.isExists()) {
            return corePlayer;
        }
        return null;
    }

    @Override
    public CorePlayer getPlayer(String name) {
        CorePlayer corePlayer = newPlayer(name);
        if (corePlayer.isExists()) {
            return corePlayer;
        }
        return null;
    }

    public abstract CorePlayer newPlayer(UUID uuid);

    public abstract CorePlayer newPlayer(String name);
}
