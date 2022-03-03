package dev.minecode.core.common.api;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.*;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.CorePluginVersion;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.common.api.manager.*;
import dev.minecode.core.common.api.object.CorePluginProvider;
import net.md_5.bungee.api.chat.BaseComponent;

public class CoreAPIProvider extends CoreAPI {

    private DatabaseManagerProvider databaseManagerProvider;
    private FileManagerProvider fileManagerProvider;
    private LanguageManagerProvider languageManagerProvider;
    private PluginManagerProvider pluginManagerProvider;

    private CorePlugin thisCorePlugin;
    private boolean usingSQL;

    public CoreAPIProvider() {
        makeInstances();
    }

    private void makeInstances() {
        CoreAPI.setInstance(this);

        thisCorePlugin = new CorePluginProvider(CoreCommon.class, "Core", "0.1.0-Pre.81", CorePluginVersion.MINECODE_CORE, false);

        fileManagerProvider = new FileManagerProvider();
        pluginManagerProvider = new PluginManagerProvider(); // requires FileManager
        languageManagerProvider = new LanguageManagerProvider(); // requires FileManager & PluginManager

        usingSQL = fileManagerProvider.getConfig().getConf().node("database", "enable").getBoolean(); // requires FileManager

        databaseManagerProvider = new DatabaseManagerProvider(); // requires FileManager & PluginManager & usingSQL
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
    public PlayerManagerProvider getPlayerManager() {
        return CoreCommon.getInstance().getPlayerManagerProvider();
    }

    @Override
    public PluginManagerProvider getPluginManager() {
        return pluginManagerProvider;
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
    public ReplaceManager getReplaceManager(Language language, String... path) {
        return new ReplaceManagerProvider(language, path);
    }

    @Override
    public ReplaceManager getReplaceManager(Language language, LanguageAbstract path) {
        return new ReplaceManagerProvider(language, path);
    }

    @Override
    public UpdateManager getUpdateManager(CorePlugin corePlugin) {
        if (UpdateManagerProvider.getUpdateManagerProviders().containsKey(corePlugin))
            return UpdateManagerProvider.getUpdateManagerProviders().get(corePlugin);
        return null;
    }

    @Override
    public CorePlugin getThisCorePlugin() {
        return thisCorePlugin;
    }

    @Override
    public boolean isUsingSQL() {
        return usingSQL;
    }
}
