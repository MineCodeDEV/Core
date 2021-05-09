package dev.minecode.core.bungeecord;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.bungeecord.manager.PlayerManagerProviderAddon;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungeeCord {
    private static CoreBungeeCord instance;

    private final String name;
    private final String version;
    private final Plugin mainClass;

    public CoreBungeeCord(String name, String version, Plugin mainClass) {
        this.name = name;
        this.version = version;
        this.mainClass = mainClass;
        makeInstances();
    }

    public static CoreBungeeCord getInstance() {
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
