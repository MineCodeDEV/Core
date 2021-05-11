package dev.minecode.core.bungeecord;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.bungeecord.manager.PlayerManagerProviderAddon;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungeeCord {
    private static CoreBungeeCord instance;

    public CoreBungeeCord() {
        makeInstances();
    }

    public static CoreBungeeCord getInstance() {
        if (instance == null) new CoreBungeeCord();
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

    public CorePlugin registerPlugin(String name, String version, Plugin mainClass) {
        return CoreAPI.getInstance().getPluginManager().registerPlugin(name, version, mainClass.getClass());
    }
}
