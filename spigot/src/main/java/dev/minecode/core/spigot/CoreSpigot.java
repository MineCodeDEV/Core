package dev.minecode.core.spigot;

import dev.minecode.core.api.object.Type;
import dev.minecode.core.common.CoreCommon;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreSpigot {
    private static CoreSpigot instance;

    private CoreCommon coreCommon;
    private JavaPlugin mainClass;

    private String pluginName;
    private String pluginVersion;

    public CoreSpigot(String pluginName, String pluginVersion, JavaPlugin mainClass) {
        this.pluginName = pluginName;
        this.pluginVersion = pluginVersion;
        this.mainClass = mainClass;

        makeInstances();
    }

    private void makeInstances() {
        instance = this;
        coreCommon = new CoreCommon(pluginName, pluginVersion);
    }

    public static CoreSpigot getInstance() {
        return instance;
    }

    public JavaPlugin getMainClass() {
        return mainClass;
    }

}