package dev.minecode.core.spigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.manager.PlayerManagerProviderAddon;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreSpigot {
    private static CoreSpigot instance;

    private JavaPlugin mainClass;

    public CoreSpigot(JavaPlugin mainClass) {
        this.mainClass = mainClass;
        makeInstances();
    }

    public static CoreSpigot getInstance() {
        return instance;
    }

    private void makeInstances() {
        instance = this;
        new CoreCommon(mainClass.getDescription().getName(), mainClass.getDescription().getVersion());
        CoreCommon.getInstance().setCorePlayerAddon(new CorePlayerAddonProvider());
        CoreAPI.getInstance().getCorePlayer(1); // Console
    }

    public void onDisable() {
        if (CoreAPI.getInstance().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
    }

    public JavaPlugin getMainClass() {
        return mainClass;
    }
}