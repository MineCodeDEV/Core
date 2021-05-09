package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlugin;

import java.util.List;

public interface PluginManager {

    CorePlugin getPlugin(String name);

    CorePlugin registerPlugin(String name, String version, Class mainClass);

    boolean unregisterPlugin(CorePlugin corePlugin);

    List<CorePlugin> getPlugins();

}
