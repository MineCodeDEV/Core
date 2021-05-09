package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlayer;

import java.util.UUID;

public interface PlayerManager {

    CorePlayer getPlayer(UUID uuid);

    CorePlayer getPlayer(String name);

}
