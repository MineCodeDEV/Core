package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.CorePluginVersion;

import java.util.List;

public interface PluginManager {

    CorePlugin getPlugin(String name);

    CorePlugin registerPlugin(Class mainClass, String name, String version, CorePluginVersion pluginVersion, boolean loadMessageFiles);

    boolean unregisterPlugin(CorePlugin corePlugin);

    List<CorePlugin> getPlugins();

}
