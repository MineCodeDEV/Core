package dev.minecode.core.bungeecord;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.common.api.object.CorePlayerProvider;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungeeCord {
    private static CoreBungeeCord instance;

    private Plugin mainClass;
    private CoreCommon coreCommon;

    public CoreBungeeCord(Plugin mainClass) {
        this.mainClass = mainClass;
        makeInstances();
    }

    private void makeInstances() {
        instance = this;
        coreCommon = new CoreCommon(mainClass.getDescription().getName(), mainClass.getDescription().getVersion());
    }

    private void onDisable() {
        for (CorePlayerProvider corePlayer : CorePlayerProvider.getIdCache().values())
            corePlayer.update();

        CoreAPI.getInstance().getFileManager().saveDatas();
        if (CoreCommon.getInstance().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
    }

    public static CoreBungeeCord getInstance() {
        return instance;
    }

    public Plugin getMainClass() {
        return mainClass;
    }
}
