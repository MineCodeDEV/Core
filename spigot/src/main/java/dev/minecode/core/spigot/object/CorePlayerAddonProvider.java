package dev.minecode.core.spigot.object;

import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.common.api.object.CorePlayerAddon;
import dev.minecode.core.spigot.api.object.CorePlayerProvider;

import java.util.UUID;

public class CorePlayerAddonProvider implements CorePlayerAddon {

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
