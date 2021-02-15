package dev.minecode.core.bungeecord;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.bungeecord.object.CorePlayerAddonProvider;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungeeCord {
    private static CoreBungeeCord instance;

    private Plugin mainClass;

    public CoreBungeeCord(Plugin mainClass) {
        this.mainClass = mainClass;
        makeInstances();
    }

    public static CoreBungeeCord getInstance() {
        return instance;
    }

    private void makeInstances() {
        instance = this;
        new CoreCommon(mainClass.getDescription().getName(), mainClass.getDescription().getVersion());
        CoreCommon.getInstance().setCorePlayerAddon(new CorePlayerAddonProvider());
        CoreAPI.getInstance().getCorePlayer(1); //CONSOLE
    }

    public void onDisable() {
        if (CoreAPI.getInstance().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
    }

    public Plugin getMainClass() {
        return mainClass;
    }
}
