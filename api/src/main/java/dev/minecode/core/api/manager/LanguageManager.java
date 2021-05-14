package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;

import java.util.List;

public interface LanguageManager {
    Object get(Language language, String... path);

    Object get(Language language, LanguageAbstract path);

    String getString(Language language, String... path);

    String getString(Language language, LanguageAbstract path);

    int getInt(Language language, String... path);

    int getInt(Language language, LanguageAbstract path);

    boolean getBoolean(Language language, String... path);

    boolean getBoolean(Language language, LanguageAbstract path);

    long getLong(Language language, String... path);

    long getLong(Language language, LanguageAbstract path);

    List<Object> getList(Language language, String... path);

    List<Object> getList(Language language, LanguageAbstract path);

    List<String> getStringList(Language language, String... path);

    List<String> getStringList(Language language, LanguageAbstract path);

    Language getLanguage(CorePlugin corePlugin, String isocode);

    List<Language> getAllLanguages(CorePlugin corePlugin);

    List<String> getAllLanguageIsocodes(CorePlugin corePlugin);

    Language getDefaultLanguage(CorePlugin corePlugin);

    String getDefaultLanguageIsocode();

    void setDefaultLanguageIsocode(CorePlugin corePlugin, String isocode);
}
