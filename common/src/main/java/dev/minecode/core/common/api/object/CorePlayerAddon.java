package dev.minecode.core.common.api.object;

import dev.minecode.core.api.object.CorePlayer;

import java.util.UUID;

public interface CorePlayerAddon {

    CorePlayer newCorePlayer(int id);

    CorePlayer newCorePlayer(UUID uuid);

    CorePlayer newCorePlayer(String name);

}
