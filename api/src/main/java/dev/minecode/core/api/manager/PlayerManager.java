package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlayer;

import java.util.UUID;

public interface PlayerManager {

    CorePlayer getCorePlayer(int id);

    CorePlayer getCorePlayer(UUID uuid);

    CorePlayer getCorePlayer(String name);

}
