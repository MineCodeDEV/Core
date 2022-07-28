package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface LanguageManager {

    // universal
    @Nullable <T> T get(@NotNull Class<T> type, @NotNull Language language, @NotNull String... path);

    @Nullable <T> T get(@NotNull Class<T> type, @NotNull Language language, @NotNull LanguageAbstract path);

    @Nullable <T> List<T> getList(@NotNull Class<T> type, @NotNull Language language, @NotNull String... path);

    @Nullable <T> List<T> getList(@NotNull Class<T> type, @NotNull Language language, @NotNull LanguageAbstract path);

    // String
    @Nullable String getString(@NotNull Language language, @NotNull String... path);

    @Nullable String getString(@NotNull Language language, @NotNull LanguageAbstract path);

    @Nullable List<String> getStringList(@NotNull Language language, @NotNull String... path);

    @Nullable List<String> getStringList(@NotNull Language language, @NotNull LanguageAbstract path);


    @Nullable Language getLanguage(@NotNull CorePlugin corePlugin, @NotNull String isocode);

    @NotNull List<Language> getLanguages(@NotNull CorePlugin corePlugin);

    @NotNull List<String> getLanguageIsocodes(@NotNull CorePlugin corePlugin);

    @NotNull Language getDefaultLanguage(@NotNull CorePlugin corePlugin);

    @NotNull String getDefaultLanguageIsocode();

    void setDefaultLanguageIsocode(@NotNull String isocode);

}
