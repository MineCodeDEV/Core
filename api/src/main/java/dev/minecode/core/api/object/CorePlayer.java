package dev.minecode.core.api.object;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface CorePlayer {

    boolean reload();

    boolean save();

    @NotNull UUID getUuid();

    @NotNull String getName();

    void setName(@NotNull String name);

    @Nullable String getLanguageIsoCode();

    @Nullable Language getLanguage(@NotNull CorePlugin corePlugin);

    void setLanguage(@Nullable String isocode);

    boolean isLanguageEmpty();


}