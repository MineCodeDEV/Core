package dev.minecode.core.spigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.api.manager.PluginMessageManagerProvider;
import dev.minecode.core.spigot.listener.PluginMessageListener;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreSpigot {
    private static CoreSpigot instance;

    private CoreCommon coreCommon;
    private JavaPlugin mainClass;

    private PluginMessageManagerProvider pluginMessageManagerProvider;

    private String pluginName;
    private String pluginVersion;

    public CoreSpigot(String pluginName, String pluginVersion, JavaPlugin mainClass) {
        this.pluginName = pluginName;
        this.pluginVersion = pluginVersion;
        this.mainClass = mainClass;

        makeInstances();
        registerChannel();
    }

    private void makeInstances() {
        instance = this;
        pluginMessageManagerProvider = new PluginMessageManagerProvider();
        coreCommon = new CoreCommon(pluginName, pluginVersion);

        coreCommon.getCoreAPIProvider().setPluginMessageManager(pluginMessageManagerProvider);
    }

    private void registerChannel() {
        mainClass.getServer().getMessenger().registerOutgoingPluginChannel(mainClass, CoreAPI.getInstance().getPluginMessageChannel());
        mainClass.getServer().getMessenger().registerIncomingPluginChannel(mainClass, CoreAPI.getInstance().getPluginMessageChannel(), new PluginMessageListener());
    }

    public static CoreSpigot getInstance() {
        return instance;
    }

    public JavaPlugin getMainClass() {
        return mainClass;
    }
}