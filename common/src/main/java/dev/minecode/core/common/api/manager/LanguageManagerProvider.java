package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.LanguageManager;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.core.common.api.object.LanguageProvider;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.ArrayList;
import java.util.List;

public class LanguageManagerProvider implements LanguageManager {

    @Override
    public Object get(String iso_code, LanguageAbstract message) {
        Object object = null;
        if (!isValid(iso_code)) iso_code = getDefaultLanguage();

        try {
            object = CoreAPI.getInstance().getLanguage(iso_code).getConfigurationNode().node(message).get(Object.class);
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        return object;
    }

    @Override
    public String getString(String iso_code, LanguageAbstract message) {
        return (String) get(iso_code, message);
    }

    @Override
    public int getInt(String iso_code, LanguageAbstract message) {
        return (int) get(iso_code, message);
    }

    @Override
    public boolean getBoolean(String iso_code, LanguageAbstract message) {
        return (boolean) get(iso_code, message);
    }

    @Override
    public long getLong(String iso_code, LanguageAbstract message) {
        return (long) get(iso_code, message);
    }

    @Override
    public List<Object> getList(String iso_code, LanguageAbstract message) {
        return (List<Object>) get(iso_code, message);
    }

    @Override
    public List<String> getStringList(String iso_code, LanguageAbstract message) {
        List<String> stringList = new ArrayList<>();
        for (Object entry : getList(iso_code, message)) stringList.add(entry.toString());
        return stringList;
    }

    @Override
    public String getName(String iso_code) {
        return CoreAPI.getInstance().getLanguage(iso_code).getName();
    }

    @Override
    public String getDisplayname(String iso_code) {
        String temp = CoreAPI.getInstance().getLanguage(iso_code).getDisplayname();
        temp = CoreAPI.getInstance().getReplaceManager(temp).chatcolorAll().getMessage();
        return temp;
    }

    @Override
    public String getTexture(String iso_code) {
        return CoreAPI.getInstance().getLanguage(iso_code).getTexture();
    }

    @Override
    public int getSlot(String iso_code) {
        return CoreAPI.getInstance().getLanguage(iso_code).getSlot();
    }

    @Override
    public List<String> getLore(String iso_code) {
        return CoreAPI.getInstance().getLanguage(iso_code).getLore();
    }

    @Override
    public String getDefaultLanguage() {
        return CoreAPI.getInstance().getFileManager().getConfig().getConf().node("language", "default").getString();
    }

    @Override
    public boolean isValid(String iso_code) {
        return CoreAPI.getInstance().getLanguage(iso_code) != null;
    }

    @Override
    public List<String> getIsoCodes() {
        return new ArrayList<>(LanguageProvider.getLanguages().keySet());
    }
}
