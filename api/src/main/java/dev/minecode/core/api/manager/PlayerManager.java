package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface PlayerManager {

    @Nullable CorePlayer getPlayer(UUID uuid);

    @Nullable CorePlayer getPlayer(String name);

}
