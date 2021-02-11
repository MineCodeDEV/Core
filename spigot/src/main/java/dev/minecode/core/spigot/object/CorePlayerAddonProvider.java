package dev.minecode.core.spigot.object;

import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.common.api.object.CorePlayerAddon;
import dev.minecode.core.spigot.api.object.CorePlayerProvider;

import java.util.ArrayList;
import java.util.UUID;

public class CorePlayerAddonProvider implements CorePlayerAddon {
    @Override
    public ArrayList<CorePlayer> getCorePlayers() {
        return CorePlayerProvider.getCorePlayers();
    }

    @Override
    public CorePlayer newCorePlayer(int id) {
        CorePlayer corePlayer = new CorePlayerProvider(id);
        getCorePlayers().add(corePlayer);
        return corePlayer;
    }

    @Override
    public CorePlayer newCorePlayer(UUID uuid) {
        CorePlayer corePlayer = new CorePlayerProvider(uuid);
        getCorePlayers().add(corePlayer);
        return corePlayer;
    }

    @Override
    public CorePlayer newCorePlayer(String name) {
        CorePlayer corePlayer = new CorePlayerProvider(name);
        getCorePlayers().add(corePlayer);
        return corePlayer;
    }
}
