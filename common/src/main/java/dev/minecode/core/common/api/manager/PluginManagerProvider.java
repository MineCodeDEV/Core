package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.manager.PluginManager;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.common.api.object.CorePluginProvider;

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
    public CorePlugin getPlugin(String name) {
        for (CorePlugin corePlugin : plugins)
            if (corePlugin.getName().equals(name))
                return corePlugin;
        return null;
    }

    @Override
    public CorePlugin registerPlugin(Class mainClass, String name, String version, boolean loadMessageFiles) {
        CorePlugin corePlugin = getPlugin(name);
        if (corePlugin != null) return corePlugin;

        corePlugin = new CorePluginProvider(mainClass, name, version, loadMessageFiles);
        plugins.add(corePlugin);

        if (loadMessageFiles) LanguageManagerProvider.loadMessageFiles(corePlugin);
        new UpdateManagerProvider(corePlugin);

        return corePlugin;
    }

    @Override
    public boolean unregisterPlugin(CorePlugin corePlugin) {
        return plugins.remove(corePlugin);
    }

    @Override
    public List<CorePlugin> getPlugins() {
        return plugins;
    }
}
