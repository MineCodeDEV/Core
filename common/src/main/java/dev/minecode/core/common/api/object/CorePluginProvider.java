package dev.minecode.core.common.api.object;

import dev.minecode.core.api.object.CorePlugin;

public class CorePluginProvider implements CorePlugin {

    String name, version;
    Class mainClass;

    public CorePluginProvider(String name, String version, Class mainClass) {
        this.name = name;
        this.version = version;
        this.mainClass = mainClass;
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
    public Class getMainClass() {
        return mainClass;
    }
}
