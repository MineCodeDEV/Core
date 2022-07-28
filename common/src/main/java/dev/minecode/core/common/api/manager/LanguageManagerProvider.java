package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.LanguageManager;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.core.common.api.object.LanguageProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
        ConfigurationNode conf = CoreAPI.getInstance().getFileManager().getConfig().getRoot();
        defaultLanguageIsocode = conf.node("language", "default").getString("en_us");
    }

    public static void loadMessageFiles(CorePlugin corePlugin) {
        File messsageDirectory = new File("plugins/" + corePlugin.getName() + "/message/");
        messsageDirectory.mkdirs();

        for (Map.Entry<Object, ? extends ConfigurationNode> node : CoreAPI.getInstance().getFileManager().getConfig().getRoot().node("language", "languages").childrenMap().entrySet())
            languages.add(new LanguageProvider(corePlugin, (String) node.getValue().key()));
    }

    private ConfigurationNode getNode(@NotNull Language language, @NotNull String... path) {
        return language.getFileObject().getRoot().node((Object[]) path);
    }

    @Override
    public @Nullable <T> T get(Class<T> type, @NotNull Language language, @NotNull String... path) {
        try {
            ConfigurationNode node = getNode(language, path);
            if (!node.empty())
                return node.get(type);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public @Nullable <T> T get(Class<T> type, @NotNull Language language, @NotNull LanguageAbstract path) {
        return get(type, language, path.getPath());
    }

    @Override
    public @Nullable <T> List<T> getList(@NotNull Class<T> type, @NotNull Language language, @NotNull String... path) {
        try {
            return getNode(language, path).getList(type);
        } catch (SerializationException ignored) {
            return null;
        }
    }

    @Override
    public @Nullable <T> List<T> getList(@NotNull Class<T> type, @NotNull Language language, @NotNull LanguageAbstract path) {
        return getList(type, language, path.getPath());
    }

    @Override
    public @Nullable String getString(@NotNull Language language, @NotNull String... path) {
        return getNode(language, path).getString();
    }

    @Override
    public @Nullable String getString(@NotNull Language language, @NotNull LanguageAbstract path) {
        return getString(language, path.getPath());
    }

    @Override
    public @Nullable List<String> getStringList(@NotNull Language language, @NotNull String... path) {
        return getList(String.class, language, path);
    }

    @Override
    public @Nullable List<String> getStringList(@NotNull Language language, @NotNull LanguageAbstract path) {
        return getStringList(language, path.getPath());
    }

    @Override
    public @Nullable Language getLanguage(@NotNull CorePlugin corePlugin, @NotNull String isocode) {
        for (LanguageProvider languageProvider : languages) {
            if (languageProvider.getIsocode().equals(isocode) && languageProvider.getPlugin() == corePlugin)
                return languageProvider;
        }
        return null;
    }

    @Override
    public @NotNull List<Language> getLanguages(@NotNull CorePlugin corePlugin) {
        ArrayList<Language> temp = new ArrayList<>();
        for (LanguageProvider languageProvider : languages)
            if (languageProvider.getPlugin().getName().equals(corePlugin.getName()))
                temp.add(languageProvider);
        return temp;
    }

    @Override
    public @NotNull List<String> getLanguageIsocodes(@NotNull CorePlugin corePlugin) {
        ArrayList<String> temp = new ArrayList<>();
        for (Language language : getLanguages(corePlugin)) temp.add(language.getName());
        return temp;
    }

    @Override
    public @NotNull Language getDefaultLanguage(@NotNull CorePlugin corePlugin) {
        return getLanguage(corePlugin, defaultLanguageIsocode);
    }

    @Override
    public @NotNull String getDefaultLanguageIsocode() {
        return defaultLanguageIsocode;
    }

    @Override
    public void setDefaultLanguageIsocode(@NotNull String isocode) {
        defaultLanguageIsocode = isocode;
    }
}
