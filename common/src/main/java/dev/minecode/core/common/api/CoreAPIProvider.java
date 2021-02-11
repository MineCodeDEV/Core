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

import java.util.Arrays;
import java.util.UUID;

public class CoreAPIProvider extends CoreAPI {

    private DatabaseManagerProvider databaseManagerProvider;
    private FileManagerProvider fileManagerProvider;
    private LanguageManagerProvider languageManagerProvider;
    private UpdateManagerProvider updateManagerProvider;

    private boolean usingSQL;
    private String defaultLanguage;

    public CoreAPIProvider() {
        makeInstances();
    }

    private void makeInstances() {
        CoreAPI.setInstance(this);
        fileManagerProvider = new FileManagerProvider();

        ConfigurationNode configNode = fileManagerProvider.getConfig().getConf();
        usingSQL = configNode.node("database", "enable").getBoolean();
        defaultLanguage = configNode.node("language", "default").getString();

        databaseManagerProvider = new DatabaseManagerProvider();
        languageManagerProvider = new LanguageManagerProvider();
        updateManagerProvider = new UpdateManagerProvider();
    }


    // API-Manager
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


    // API-Objects
    @Override
    public CorePlayer getCorePlayer(int id) {
        for (CorePlayer corePlayer : CoreCommon.getInstance().getCorePlayerAddon().getCorePlayers()) {
            if (corePlayer.getID() == id) {
                corePlayer.reload();
                return corePlayer;
            }
        }

        CorePlayer corePlayer = CoreCommon.getInstance().getCorePlayerAddon().newCorePlayer(id);
        if (corePlayer.isExists()) {
            CoreCommon.getInstance().getCorePlayerAddon().getCorePlayers().add(corePlayer);
            return corePlayer;
        }
        return null;
    }

    @Override
    public CorePlayer getCorePlayer(UUID uuid) {
        for (CorePlayer corePlayer : CoreCommon.getInstance().getCorePlayerAddon().getCorePlayers()) {
            if (corePlayer.getUuid() == uuid) {
                corePlayer.reload();
                return corePlayer;
            }
        }

        CorePlayer corePlayer = CoreCommon.getInstance().getCorePlayerAddon().newCorePlayer(uuid);
        if (corePlayer.isExists()) {
            CoreCommon.getInstance().getCorePlayerAddon().getCorePlayers().add(corePlayer);
            return corePlayer;
        }
        return null;
    }

    @Override
    public CorePlayer getCorePlayer(String name) {
        for (CorePlayer corePlayer : CoreCommon.getInstance().getCorePlayerAddon().getCorePlayers()) {
            if (corePlayer.getName().equals(name)) {
                corePlayer.reload();
                return corePlayer;
            }
        }

        CorePlayer corePlayer = CoreCommon.getInstance().getCorePlayerAddon().newCorePlayer(name);
        if (corePlayer.isExists()) {
            CoreCommon.getInstance().getCorePlayerAddon().getCorePlayers().add(corePlayer);
            return corePlayer;
        }
        return null;
    }

    @Override
    public FileObject getFileObject(String fileName, String pluginName, String... folders) {
        if (FileObjectProvider.getFileObjects().containsKey(fileName + Arrays.toString(folders)))
            return FileObjectProvider.getFileObjects().get(fileName);
        return new FileObjectProvider(fileName, pluginName, folders);
    }

    @Override
    public FileObject getFileObject(String fileName, String pluginName) {
        if (FileObjectProvider.getFileObjects().containsKey(fileName))
            return FileObjectProvider.getFileObjects().get(fileName);
        return new FileObjectProvider(fileName, pluginName);
    }

    @Override
    public Language getLanguage(String iso_code) {
        if (LanguageProvider.getLanguages().containsKey(iso_code))
            return LanguageProvider.getLanguages().get(iso_code);
        return null;
    }


    // Variables
    @Override
    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    @Override
    public String getPluginName() {
        return CoreCommon.getInstance().getPluginName();
    }

    @Override
    public boolean isUsingSQL() {
        return usingSQL;
    }
}
