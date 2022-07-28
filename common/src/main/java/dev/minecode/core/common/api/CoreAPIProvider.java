package dev.minecode.core.common.api;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.*;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.CorePluginSoftware;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.common.api.manager.*;
import dev.minecode.core.common.api.object.CorePluginProvider;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;

public class CoreAPIProvider extends CoreAPI {

    private DatabaseManagerProvider databaseManagerProvider;
    private FileManagerProvider fileManagerProvider;
    private LanguageManagerProvider languageManagerProvider;
    private PlayerManager playerManager;
    private PluginManagerProvider pluginManagerProvider;
    private HashMap<CorePlugin, UpdateManagerProvider> updateManagerProviders;

    private CorePlugin corePlugin;

    public CoreAPIProvider() {
        makeInstances();
    }

    private void makeInstances() {
        CoreAPI.setInstance(this);

        corePlugin = new CorePluginProvider(CoreCommon.class, "Core", "0.1.0-Pre.83", CorePluginSoftware.MINECODE_CORE, new File("plugins/Core/"), false);

        fileManagerProvider = new FileManagerProvider(); // requires thisCorePlugin
        pluginManagerProvider = new PluginManagerProvider(); // requires fileManagerProvider
        databaseManagerProvider = new DatabaseManagerProvider(); // requires fileManagerProvider
        languageManagerProvider = new LanguageManagerProvider(); // requires fileManagerProvider & pluginManagerProvider
        updateManagerProviders = new HashMap<>();
    }

    // Manager
    @Override
    public @NotNull DatabaseManager getDatabaseManager() {
        return databaseManagerProvider;
    }

    @Override
    public @NotNull FileManager getFileManager() {
        return fileManagerProvider;
    }

    @Override
    public @NotNull LanguageManager getLanguageManager() {
        return languageManagerProvider;
    }

    @Override
    public @NotNull PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public void setPlayerManager(@NotNull PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public @NotNull PluginManagerProvider getPluginManager() {
        return pluginManagerProvider;
    }

    @Override
    public @NotNull ReplaceManager getReplaceManager(@NotNull String message) {
        return new ReplaceManagerProvider(message);
    }

    @Override
    public @NotNull ReplaceManager getReplaceManager(@NotNull BaseComponent[] message) {
        return new ReplaceManagerProvider(message);
    }

    @Override
    public @NotNull ReplaceManager getReplaceManager(@NotNull Language language, String... path) {
        return new ReplaceManagerProvider(language, path);
    }

    @Override
    public @NotNull ReplaceManager getReplaceManager(@NotNull Language language, @NotNull LanguageAbstract path) {
        return new ReplaceManagerProvider(language, path);
    }

    @Override
    public @NotNull UpdateManager getUpdateManager(@NotNull CorePlugin corePlugin) {
        if (updateManagerProviders.containsKey(corePlugin))
            return updateManagerProviders.get(corePlugin);

        UpdateManagerProvider updateManagerProvider = new UpdateManagerProvider(corePlugin);
        updateManagerProviders.put(corePlugin, updateManagerProvider);
        return updateManagerProvider;
    }

    @Override
    public @NotNull CorePlugin getCorePlugin() {
        return corePlugin;
    }
}
