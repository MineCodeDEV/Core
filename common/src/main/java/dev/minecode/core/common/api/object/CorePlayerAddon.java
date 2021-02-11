package dev.minecode.core.common.api.object;

import dev.minecode.core.api.object.CorePlayer;

import java.util.ArrayList;
import java.util.UUID;

public interface CorePlayerAddon {

    ArrayList<CorePlayer> getCorePlayers();

    CorePlayer newCorePlayer(int id);

    CorePlayer newCorePlayer(UUID uuid);

    CorePlayer newCorePlayer(String name);

}
