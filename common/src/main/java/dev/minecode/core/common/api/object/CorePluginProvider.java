package dev.minecode.core.common.api.object;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.PluginPlattform;
import dev.minecode.core.common.api.manager.LanguageManagerProvider;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class CorePluginProvider implements CorePlugin {

    private final Class mainClass;
    private final String name;
    private final String version;
    private final PluginPlattform pluginVersion;
    private final File dataFolder;
    private boolean loadMessageFiles;

    public CorePluginProvider(Class mainClass, String name, String version, PluginPlattform pluginVersion, File dataFolder, boolean loadMessageFiles) {
        this.mainClass = mainClass;
        this.name = name;
        this.version = version;
        this.pluginVersion = pluginVersion;
        this.dataFolder = dataFolder;
        this.loadMessageFiles = loadMessageFiles;
    }

    @Override
    public @NotNull Class getMainClass() {
        return mainClass;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String getVersion() {
        return version;
    }

    @Override
    public @NotNull PluginPlattform getPlattform() {
        return pluginVersion;
    }

    @Override
    public @NotNull File getDataFolder() {
        return dataFolder;
    }

    @Override
    public boolean isLoadMessageFiles() {
        return loadMessageFiles;
    }

    @Override
    public void loadMessageFiles() {
        LanguageManagerProvider.loadMessageFiles(this);
        loadMessageFiles = true;
    }
}
