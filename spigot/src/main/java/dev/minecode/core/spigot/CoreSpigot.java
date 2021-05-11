package dev.minecode.core.spigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.manager.PlayerManagerProviderAddon;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreSpigot {
    private static CoreSpigot instance;

    public CoreSpigot() {
        makeInstances();
    }

    public static CoreSpigot getInstance() {
        if (instance == null) new CoreSpigot();
        return instance;
    }

    private void makeInstances() {
        instance = this;
        CoreCommon.getInstance().setPlayerManagerProvider(new PlayerManagerProviderAddon());
    }

    public void onDisable() {
        if (CoreAPI.getInstance().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
        CoreAPI.getInstance().getFileManager().saveDatas();
    }

    public CorePlugin registerPlugin(String name, String version, JavaPlugin mainClass) {
        return CoreAPI.getInstance().getPluginManager().registerPlugin(name, version, mainClass.getClass());
    }
}
