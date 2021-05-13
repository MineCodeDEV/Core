package dev.minecode.core.api.object;

import java.util.UUID;

public interface CorePlayer {

    boolean reload();

    boolean save();

    UUID getUuid();

    boolean setUuid(UUID uuid);

    String getName();

    boolean setName(String name);

    Language getLanguage(CorePlugin corePlugin);

    String getLanguageIsoCode();

    void setLanguage(String isocode);

    boolean isLanguageEmpty();

    boolean isExists();

}