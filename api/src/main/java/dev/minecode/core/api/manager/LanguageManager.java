package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;

import java.util.List;

public interface LanguageManager {

    Object get(Language language, LanguageAbstract message);

    String getString(Language language, LanguageAbstract message);

    int getInt(Language language, LanguageAbstract message);

    boolean getBoolean(Language language, LanguageAbstract message);

    long getLong(Language language, LanguageAbstract message);

    List<Object> getList(Language language, LanguageAbstract message);

    List<String> getStringList(Language language, LanguageAbstract message);

    Language getLanguage(CorePlugin corePlugin, String isocode);

    List<Language> getAllLanguages(CorePlugin corePlugin);

    List<String> getAllLanguageIsocodes(CorePlugin corePlugin);

    Language getDefaultLanguage(CorePlugin corePlugin);

    String getDefaultLanguageIsocode();
}
