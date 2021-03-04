package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.LanguageManager;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.core.common.api.object.LanguageProvider;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageManagerProvider implements LanguageManager {

    private static HashMap<String, LanguageProvider> languages = new HashMap<>();

    private Language defaultLanguage;

    public LanguageManagerProvider() {
        loadMessageFiles();
    }

    public static HashMap<String, LanguageProvider> getLanguages() {
        return languages;
    }

    private void loadMessageFiles() {
        File messsageDirectory = new File("plugins/MineCode/" + CoreAPI.getInstance().getPluginManager().getPluginName() + "/message/");
        messsageDirectory.mkdirs();

        for (Map.Entry<Object, ? extends ConfigurationNode> node : CoreAPI.getInstance().getFileManager().getConfig().getConf().node("language", "languages").childrenMap().entrySet()) {
            new LanguageProvider((String) node.getValue().key());
        }

        ConfigurationNode conf = CoreAPI.getInstance().getFileManager().getConfig().getConf();
        defaultLanguage = getLanguage(conf.node("language", "default").getString());
    }

    @Override
    public Object get(Language language, LanguageAbstract message) {
        if (language == null) language = CoreAPI.getInstance().getLanguageManager().getDefaultLanguage();

        try {
            ConfigurationNode tempNode = language.getConfigurationNode().node(message.getPath());
            if (!tempNode.empty())
                return tempNode.get(Object.class);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getString(Language language, LanguageAbstract message) {
        return String.valueOf(get(language, message));
    }

    @Override
    public int getInt(Language language, LanguageAbstract message) {
        return Integer.parseInt(String.valueOf(get(language, message)));
    }

    @Override
    public boolean getBoolean(Language language, LanguageAbstract message) {
        return Boolean.parseBoolean(String.valueOf(get(language, message)));
    }

    @Override
    public long getLong(Language language, LanguageAbstract message) {
        return Long.parseLong(String.valueOf(get(language, message)));
    }

    @Override
    public List<Object> getList(Language language, LanguageAbstract message) {
        return (List<Object>) get(language, message);
    }

    @Override
    public List<String> getStringList(Language language, LanguageAbstract message) {
        return (List<String>) get(language, message);
    }

    @Override
    public Language getLanguage(String isocode) {
        if (languages.containsKey(isocode))
            return languages.get(isocode);
        return null;
    }

    @Override
    public List<Language> getAllLanguages() {
        return new ArrayList<>(languages.values());
    }

    @Override
    public Language getDefaultLanguage() {
        return defaultLanguage;
    }
}
