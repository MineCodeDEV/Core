package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.manager.PlayerManager;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.common.api.object.CorePlayerProvider;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerManagerProvider implements PlayerManager {
    @Override
    public @Nullable CorePlayer getPlayer(UUID uuid) {
        CorePlayerProvider corePlayerProvider = new CorePlayerProvider(uuid);
        if (corePlayerProvider.isExists())
            return corePlayerProvider;
        return null;
    }

    @Override
    public @Nullable CorePlayer getPlayer(String name) {
        CorePlayerProvider corePlayerProvider = new CorePlayerProvider(name);
        if (corePlayerProvider.isExists())
            return corePlayerProvider;
        return null;
    }
}
