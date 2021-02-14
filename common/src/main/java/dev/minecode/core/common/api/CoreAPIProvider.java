package dev.minecode.core.common.api;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.*;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.common.api.manager.*;
import dev.minecode.core.common.api.object.FileObjectProvider;
import dev.minecode.core.common.api.object.LanguageProvider;
import net.md_5.bungee.api.chat.BaseComponent;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CoreAPIProvider extends CoreAPI {

    private DatabaseManagerProvider databaseManagerProvider;
    private FileManagerProvider fileManagerProvider;
    private LanguageManagerProvider languageManagerProvider;
    private UpdateManagerProvider updateManagerProvider;

    private boolean usingSQL;
    private Language defaultLanguage;

    public CoreAPIProvider() {
        makeInstances();
    }

    private void makeInstances() {
        CoreAPI.setInstance(this);
        fileManagerProvider = new FileManagerProvider();

        ConfigurationNode configNode = fileManagerProvider.getConfig().getConf();
        usingSQL = configNode.node("database", "enable").getBoolean();
        defaultLanguage = getLanguage(configNode.node("language", "default").getString());

        databaseManagerProvider = new DatabaseManagerProvider();
        languageManagerProvider = new LanguageManagerProvider();
        updateManagerProvider = new UpdateManagerProvider();
    }


    // Manager
    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManagerProvider;
    }

    @Override
    public FileManager getFileManager() {
        return fileManagerProvider;
    }

    @Override
    public LanguageManager getLanguageManager() {
        return languageManagerProvider;
    }

    @Override
    public ReplaceManager getReplaceManager(String message) {
        return new ReplaceManagerProvider(message);
    }

    @Override
    public ReplaceManager getReplaceManager(BaseComponent[] message) {
        return new ReplaceManagerProvider(message);
    }

    @Override
    public ReplaceManager getReplaceManager(Language language, LanguageAbstract path) {
        return new ReplaceManagerProvider(language, path);
    }

    @Override
    public UpdateManager getUpdateManager() {
        return updateManagerProvider;
    }


    // Objects
    @Override
    public CorePlayer getCorePlayer(int id) {
        CorePlayer corePlayer = CoreCommon.getInstance().getCorePlayerAddon().newCorePlayer(id);
        if (corePlayer.isExists()) {
            return corePlayer;
        }
        return null;
    }

    @Override
    public CorePlayer getCorePlayer(UUID uuid) {
        CorePlayer corePlayer = CoreCommon.getInstance().getCorePlayerAddon().newCorePlayer(uuid);
        if (corePlayer.isExists()) {
            return corePlayer;
        }
        return null;
    }

    @Override
    public CorePlayer getCorePlayer(String name) {
        CorePlayer corePlayer = CoreCommon.getInstance().getCorePlayerAddon().newCorePlayer(name);
        if (corePlayer.isExists()) {
            return corePlayer;
        }
        return null;
    }

    @Override
    public FileObject getFileObject(String fileName, String pluginName, String... folders) {
        if (FileObjectProvider.getFileObjects().containsKey(fileName + pluginName + Arrays.toString(folders)))
            return FileObjectProvider.getFileObjects().get(fileName + pluginName + Arrays.toString(folders));

        FileObjectProvider fileObjectProvider = new FileObjectProvider(fileName, pluginName, folders);
        FileObjectProvider.getFileObjects().put(fileName + pluginName + Arrays.toString(folders), fileObjectProvider);
        return fileObjectProvider;
    }

    @Override
    public FileObject getFileObject(String fileName, String pluginName) {
        if (FileObjectProvider.getFileObjects().containsKey(fileName + pluginName))
            return FileObjectProvider.getFileObjects().get(fileName + pluginName);
        FileObjectProvider fileObjectProvider = new FileObjectProvider(fileName, pluginName);
        FileObjectProvider.getFileObjects().put(fileName + pluginName, fileObjectProvider);
        return fileObjectProvider;
    }

    @Override
    public Language getLanguage(String iso_code) {
        if (LanguageProvider.getLanguages().containsKey(iso_code))
            return LanguageProvider.getLanguages().get(iso_code);
        return null;
    }

    @Override
    public List<Language> getLanguages() {
        return new ArrayList<>(LanguageProvider.getLanguages().values());
    }

    @Override
    public Language getDefaultLanguage() {
        return defaultLanguage;
    }


    // Variables

    @Override
    public String getPluginName() {
        return CoreCommon.getInstance().getPluginName();
    }

    @Override
    public String getPluginVersion() {
        return CoreCommon.getInstance().getPluginVersion();
    }

    @Override
    public boolean isUsingSQL() {
        return usingSQL;
    }
}
