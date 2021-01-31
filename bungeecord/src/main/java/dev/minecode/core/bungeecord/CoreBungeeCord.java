package dev.minecode.core.bungeecord;

import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungeeCord {
    private static CoreBungeeCord instance;

    private CoreCommon coreCommon;
    private Plugin mainClass;

    private String pluginName;
    private String pluginVersion;

    public CoreBungeeCord(String pluginName, String pluginVersion, Plugin mainClass) {
        this.pluginName = pluginName;
        this.pluginVersion = pluginVersion;
        this.mainClass = mainClass;
        makeInstances();
    }

    private void makeInstances() {
        instance = this;
        coreCommon = new CoreCommon(pluginName, pluginVersion);
    }

    public static CoreBungeeCord getInstance() {
        return instance;
    }

    public Plugin getMainClass() {
        return mainClass;
    }
}
