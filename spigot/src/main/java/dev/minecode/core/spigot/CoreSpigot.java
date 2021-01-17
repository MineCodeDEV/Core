package dev.minecode.core.spigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.api.manager.PluginMessageManagerProvider;
import dev.minecode.core.spigot.listener.PluginMessageListener;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreSpigot {

    private static CoreSpigot instance;
    private JavaPlugin mainClass;

    public CoreSpigot(String pluginName, String pluginVersion, JavaPlugin mainClass) {
        instance = this;
        this.mainClass = mainClass;
        PluginMessageManagerProvider pluginMessageManager = new PluginMessageManagerProvider();
        new CoreCommon(pluginName, pluginVersion);
        CoreAPI.getInstance().setPluginMessageManager(pluginMessageManager);


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
