package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.CorePluginSoftware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public interface PluginManager {

    @Nullable CorePlugin getPlugin(@NotNull String name);

    @NotNull CorePlugin registerPlugin(@NotNull Class mainClass, @NotNull String name, @NotNull String version, @NotNull File dataFolder, @NotNull CorePluginSoftware pluginVersion, boolean loadMessageFiles);

    boolean unregisterPlugin(@NotNull CorePlugin corePlugin);

    @NotNull List<CorePlugin> getPlugins();

}
