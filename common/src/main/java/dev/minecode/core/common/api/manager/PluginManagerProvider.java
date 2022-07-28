package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.manager.PluginManager;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.CorePluginSoftware;
import dev.minecode.core.common.api.object.CorePluginProvider;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginManagerProvider implements PluginManager {

    private ArrayList<CorePlugin> plugins;

    public PluginManagerProvider() {
        makeInstances();
    }

    private void makeInstances() {
        plugins = new ArrayList<>();
    }

    @Override
    public CorePlugin getPlugin(@NotNull String name) {
        for (CorePlugin corePlugin : plugins)
            if (corePlugin.getName().equals(name))
                return corePlugin;
        return null;
    }

    @Override
    public @NotNull CorePlugin registerPlugin(@NotNull Class mainClass, @NotNull String name, @NotNull String version, @NotNull File dataFolder, @NotNull CorePluginSoftware pluginVersion, boolean loadMessageFiles) {
        CorePlugin corePlugin = getPlugin(name);
        if (corePlugin != null) return corePlugin;

        corePlugin = new CorePluginProvider(mainClass, name, version, pluginVersion, dataFolder, loadMessageFiles);
        plugins.add(corePlugin);

        if (loadMessageFiles) LanguageManagerProvider.loadMessageFiles(corePlugin);

        new UpdateManagerProvider(corePlugin);

        return corePlugin;
    }

    @Override
    public boolean unregisterPlugin(@NotNull CorePlugin corePlugin) {
        return plugins.remove(corePlugin);
    }

    @Override
    public @NotNull List<CorePlugin> getPlugins() {
        return plugins;
    }
}
