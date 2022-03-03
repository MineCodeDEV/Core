package dev.minecode.core.common.api.object;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.CorePluginVersion;
import dev.minecode.core.common.api.manager.LanguageManagerProvider;

public class CorePluginProvider implements CorePlugin {

    Class mainClass;
    String name, version;
    CorePluginVersion pluginVersion;
    boolean loadMessageFiles;

    public CorePluginProvider(Class mainClass, String name, String version, CorePluginVersion pluginVersion, boolean loadMessageFiles) {
        this.mainClass = mainClass;
        this.name = name;
        this.version = version;
        this.pluginVersion = pluginVersion;
        this.loadMessageFiles = loadMessageFiles;
    }

    @Override
    public Class getMainClass() {
        return mainClass;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public CorePluginVersion getPluginVersion() {
        return pluginVersion;
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
