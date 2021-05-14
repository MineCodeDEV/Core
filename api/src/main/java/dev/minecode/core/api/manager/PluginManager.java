package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlugin;

import java.util.List;

public interface PluginManager {

    CorePlugin getPlugin(String name);

    CorePlugin registerPlugin(Class mainClass, String name, String version, boolean loadMessageFiles);

    boolean unregisterPlugin(CorePlugin corePlugin);

    List<CorePlugin> getPlugins();

}
