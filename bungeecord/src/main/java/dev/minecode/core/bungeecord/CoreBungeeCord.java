package dev.minecode.core.bungeecord;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.bungeecord.manager.PlayerManagerProviderAddon;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungeeCord {
    private static CoreBungeeCord instance;

    private final Plugin mainClass;

    public CoreBungeeCord(Plugin mainClass) {
        this.mainClass = mainClass;
        makeInstances();
    }

    public static CoreBungeeCord getInstance() {
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

    public Plugin getMainClass() {
        return mainClass;
    }
}
