package dev.minecode.core.api.object;

import java.util.UUID;

public interface CorePlayer {

    boolean reload();

    boolean save();

    int getID();

    boolean setID(int id);

    UUID getUuid();

    boolean setUuid(UUID uuid);

    String getName();

    boolean setName(String name);

    Language getLanguage();

    void setLanguage(Language language);

    boolean isExists();

}