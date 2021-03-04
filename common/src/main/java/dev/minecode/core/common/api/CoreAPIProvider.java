package dev.minecode.core.common.api;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.*;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.common.api.manager.*;
import net.md_5.bungee.api.chat.BaseComponent;

public class CoreAPIProvider extends CoreAPI {

    private DatabaseManagerProvider databaseManagerProvider;
    private FileManagerProvider fileManagerProvider;
    private LanguageManagerProvider languageManagerProvider;
    private PluginManagerProvider pluginManagerProvider;
    private UpdateManagerProvider updateManagerProvider;

    public CoreAPIProvider() {
        makeInstances();
    }

    private void makeInstances() {
        CoreAPI.setInstance(this);
        fileManagerProvider = new FileManagerProvider();
        pluginManagerProvider = new PluginManagerProvider(); // requires FileManager
        databaseManagerProvider = new DatabaseManagerProvider(); // requires FileManager & PluginManager
        languageManagerProvider = new LanguageManagerProvider(); // requires FileManager & PluginManager
        updateManagerProvider = new UpdateManagerProvider(); // requires FileManager
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
}
