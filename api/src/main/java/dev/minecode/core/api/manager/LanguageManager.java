package dev.minecode.core.api.manager;

import java.util.List;

public interface LanguageManager {

    Object get(String iso_code, String... path);

    String getString(String iso_code, String... path);

    int getInt(String iso_code, String... path);

    boolean getBoolean(String iso_code, String... path);

    long getLong(String iso_code, String... path);

    List<Object> getList(String iso_code, String... path);

    // spezifischere Methoden

    List<String> getStringList(String iso_code, String... path);

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
