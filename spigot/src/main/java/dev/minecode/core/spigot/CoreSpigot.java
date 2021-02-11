package dev.minecode.core.spigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.object.CorePlayerAddonProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class CoreSpigot {
    private static CoreSpigot instance;

    private JavaPlugin mainClass;

    public CoreSpigot(JavaPlugin mainClass) {
        this.mainClass = mainClass;
        makeInstances();
    }

    private void makeInstances() {
        instance = this;
        new CoreCommon(mainClass.getDescription().getName(), mainClass.getDescription().getVersion());
        CoreCommon.getInstance().setCorePlayerAddon(new CorePlayerAddonProvider());

        CoreAPI.getInstance().getCorePlayer(new UUID(0, 0)); // Console
    }

    public void onDisable() {
        if (CoreAPI.getInstance().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
    }

    public static CoreSpigot getInstance() {
        return instance;
    }

    public JavaPlugin getMainClass() {
        return mainClass;
    }

}