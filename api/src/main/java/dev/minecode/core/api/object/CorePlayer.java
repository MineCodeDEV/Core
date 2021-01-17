package dev.minecode.core.api.object;

import java.util.UUID;

public interface CorePlayer {

    void update();

    void reload();

    int getID();

    void setID(int id);

    UUID getUuid();

    void setUuid(UUID uuid);

    String getName();

    void setName(String name);

    String getLanguage();

    void setLanguage(String language);

}