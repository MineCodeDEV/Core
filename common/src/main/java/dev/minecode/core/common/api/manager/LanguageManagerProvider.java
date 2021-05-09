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
    }

    public static ArrayList<LanguageProvider> getLanguages() {
        return languages;
    }

    public void loadMessageFiles(CorePlugin corePlugin) {
        File messsageDirectory = new File("plugins/MineCode/" + corePlugin.getName() + "/message/");
        messsageDirectory.mkdirs();

        for (Map.Entry<Object, ? extends ConfigurationNode> node : CoreAPI.getInstance().getFileManager().getConfig().getConf().node("language", "languages").childrenMap().entrySet()) {
            new LanguageProvider(corePlugin, (String) node.getValue().key());
        }

        ConfigurationNode conf = CoreAPI.getInstance().getFileManager().getConfig().getConf();
        defaultLanguageIsocode = conf.node("language", "default").getString();
    }

    @Override
    public Object get(Language language, LanguageAbstract message) {
        if (language == null)
            language = CoreAPI.getInstance().getLanguageManager().getDefaultLanguage(CoreAPI.getInstance().getThisCorePlugin());

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
}
