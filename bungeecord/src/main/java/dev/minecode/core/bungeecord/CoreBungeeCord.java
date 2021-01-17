package dev.minecode.core.bungeecord;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.bungeecord.api.manager.PluginMessageManagerProvider;
import dev.minecode.core.bungeecord.listener.PluginMessageListener;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungeeCord {
    private static CoreBungeeCord instance;
    private Plugin mainClass;

    public CoreBungeeCord(String pluginName, String pluginVersion, Plugin mainClass) {
        instance = this;
        this.mainClass = mainClass;
        PluginMessageManagerProvider pluginMessageManager = new PluginMessageManagerProvider();
        new CoreCommon(pluginName, pluginVersion);
        CoreAPI.getInstance().setPluginMessageManager(pluginMessageManager);

        mainClass.getProxy().registerChannel(CoreAPI.getInstance().getPluginMessageChannel());
        mainClass.getProxy().getPluginManager().registerListener(mainClass, new PluginMessageListener());

    }

    public static CoreBungeeCord getInstance() {
        return instance;
    }

    public Plugin getMainClass() {
        return mainClass;
    }
}
