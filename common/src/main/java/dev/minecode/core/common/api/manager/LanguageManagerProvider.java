package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.LanguageManager;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.core.common.api.object.LanguageProvider;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LanguageManagerProvider implements LanguageManager {

    private static final ArrayList<LanguageProvider> languages = new ArrayList<>();

    private String defaultLanguageIsocode;

    public LanguageManagerProvider() {
        ConfigurationNode conf = CoreAPI.getInstance().getFileManager().getConfig().getConf();
        defaultLanguageIsocode = conf.node("language", "default").getString();
    }

    public static ArrayList<LanguageProvider> getLanguages() {
        return languages;
    }

    public static void loadMessageFiles(CorePlugin corePlugin) {
        File messsageDirectory = new File("plugins/" + corePlugin.getName() + "/message/");
        messsageDirectory.mkdirs();

        for (Map.Entry<Object, ? extends ConfigurationNode> node : CoreAPI.getInstance().getFileManager().getConfig().getConf().node("language", "languages").childrenMap().entrySet()) {
            new LanguageProvider(corePlugin, (String) node.getValue().key());
        }
    }

    @Override
    public Object get(Language language, String... path) {

        try {
            ConfigurationNode tempNode = language.getFileObject().getConf().node(path);
            if (!tempNode.empty())
                return tempNode.get(Object.class);

            return tempNode.path();
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object get(Language language, LanguageAbstract path) {
        return get(language, path.getPath());
    }

    @Override
    public String getString(Language language, String... path) {
        return String.valueOf(get(language, path));
    }

    @Override
    public String getString(Language language, LanguageAbstract path) {
        return String.valueOf(get(language, path));
    }

    @Override
    public int getInt(Language language, String... path) {
        return Integer.parseInt(String.valueOf(get(language, path)));
    }

    @Override
    public int getInt(Language language, LanguageAbstract path) {
        return Integer.parseInt(String.valueOf(get(language, path)));
    }

    @Override
    public boolean getBoolean(Language language, String... path) {
        return Boolean.parseBoolean(String.valueOf(get(language, path)));
    }

    @Override
    public boolean getBoolean(Language language, LanguageAbstract path) {
        return Boolean.parseBoolean(String.valueOf(get(language, path)));
    }

    @Override
    public long getLong(Language language, String... path) {
        return Long.parseLong(String.valueOf(get(language, path)));
    }

    @Override
    public long getLong(Language language, LanguageAbstract path) {
        return Long.parseLong(String.valueOf(get(language, path)));
    }

    @Override
    public List<Object> getList(Language language, String... path) {
        return (List<Object>) get(language, path);
    }

    @Override
    public List<Object> getList(Language language, LanguageAbstract path) {
        return (List<Object>) get(language, path);
    }

    @Override
    public List<String> getStringList(Language language, String... path) {
        return (List<String>) get(language, path);
    }

    @Override
    public List<String> getStringList(Language language, LanguageAbstract path) {
        return (List<String>) get(language, path);
    }

    @Override
    public Language getLanguage(CorePlugin corePlugin, String isocode) {
        for (LanguageProvider languageProvider : languages) {
            if (languageProvider.getIsocode().equals(isocode) && languageProvider.getPlugin() == corePlugin)
                return languageProvider;
        }
        return null;
    }

    @Override
    public List<Language> getAllLanguages(CorePlugin corePlugin) {
        ArrayList<Language> temp = new ArrayList<>();
        for (LanguageProvider languageProvider : languages)
            if (languageProvider.getPlugin().getName().equals(corePlugin.getName()))
                temp.add(languageProvider);
        return temp;
    }

    @Override
    public List<String> getAllLanguageIsocodes(CorePlugin corePlugin) {
        ArrayList<String> temp = new ArrayList<>();
        for (Language language : getAllLanguages(corePlugin)) temp.add(language.getName());
        return temp;
    }

    @Override
    public Language getDefaultLanguage(CorePlugin corePlugin) {
        return getLanguage(corePlugin, defaultLanguageIsocode);
    }

    @Override
    public String getDefaultLanguageIsocode() {
        return defaultLanguageIsocode;
    }

    @Override
    public void setDefaultLanguageIsocode(String isocode) {
        defaultLanguageIsocode = isocode;
    }
}
