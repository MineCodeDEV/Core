package dev.minecode.core.bungeecord;

import dev.minecode.core.common.CoreCommon;
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

    public static CoreBungeeCord getInstance() {
        return instance;
    }

    public Plugin getMainClass() {
        return mainClass;
    }
}
