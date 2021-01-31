package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.LanguageAbstract;

import java.util.List;

public interface LanguageManager {

    Object get(String iso_code, LanguageAbstract message);

    String getString(String iso_code, LanguageAbstract message);

    int getInt(String iso_code, LanguageAbstract message);

    boolean getBoolean(String iso_code, LanguageAbstract message);

    long getLong(String iso_code, LanguageAbstract message);

    List<Object> getList(String iso_code, LanguageAbstract message);

    // spezifischere Methoden

    List<String> getStringList(String iso_code, LanguageAbstract message);

    // Methoden zur Sprache

    String getName(String iso_code);

    String getDisplayname(String iso_code);

    String getTexture(String iso_code);

    int getSlot(String iso_code);

    List<String> getLore(String iso_code);

    String getDefaultLanguage();

    boolean isValid(String iso_code);

    List<String> getIsoCodes();

}
