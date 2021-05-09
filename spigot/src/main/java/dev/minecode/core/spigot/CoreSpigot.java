package dev.minecode.core.spigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.manager.PlayerManagerProviderAddon;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreSpigot {
    private static CoreSpigot instance;

    private final String name;
    private final String version;
    private final JavaPlugin mainClass;

    public CoreSpigot(String name, String version, JavaPlugin mainClass) {
        this.name = name;
        this.version = version;
        this.mainClass = mainClass;
        makeInstances();
    }

    public static CoreSpigot getInstance() {
        return instance;
    }

    private void makeInstances() {
        instance = this;
        CoreCommon.getInstance().setPlayerManagerProvider(new PlayerManagerProviderAddon());
        CoreAPI.getInstance().getPluginManager().registerPlugin(name, version, mainClass.getClass());
    }

    public void onDisable() {
        if (CoreAPI.getInstance().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
        CoreAPI.getInstance().getFileManager().saveDatas();
    }
}
