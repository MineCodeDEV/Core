package dev.minecode.core.bungeecord;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.bungeecord.api.object.CorePlayerProvider;
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

    private void makeInstances() {
        instance = this;
        new CoreCommon(mainClass.getDescription().getName(), mainClass.getDescription().getVersion());
        CoreCommon.getInstance().setCorePlayerAddon(new CorePlayerAddonProvider());
    }

    public void onDisable() {
        for (CorePlayer corePlayer : CorePlayerProvider.getCorePlayers())
            corePlayer.save();

        CoreAPI.getInstance().getFileManager().saveDatas();
        if (CoreAPI.getInstance().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
    }

    public static CoreBungeeCord getInstance() {
        return instance;
    }

    public Plugin getMainClass() {
        return mainClass;
    }
}
