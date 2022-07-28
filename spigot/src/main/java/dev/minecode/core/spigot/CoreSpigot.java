package dev.minecode.core.spigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.CorePluginSoftware;
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
    }

    public void onDisable() {
        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
        else
            CoreAPI.getInstance().getFileManager().saveData();
    }

    public CorePlugin registerPlugin(JavaPlugin mainClass, boolean loadMessageFiles) {
        return CoreAPI.getInstance().getPluginManager().registerPlugin(mainClass.getClass(), mainClass.getDescription().getName(), mainClass.getDescription().getVersion(), mainClass.getDataFolder(), CorePluginSoftware.SPIGOT, loadMessageFiles);
    }
}
