package dev.minecode.core.spigot;

import dev.minecode.core.api.object.Type;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.api.manager.PluginMessageManagerProvider;
import dev.minecode.core.spigot.listener.PlayerListener;
import dev.minecode.core.spigot.listener.PluginMessageListener;
import org.bukkit.Bukkit;
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
        registerListener();
    }

    private void makeInstances() {
        instance = this;
        pluginMessageManagerProvider = new PluginMessageManagerProvider();
        coreCommon = new CoreCommon(pluginName, pluginVersion);
        CoreCommon.getInstance().setProcessName(Bukkit.getName()); //TODO: Better determine ProcessName
        CoreCommon.getInstance().setProcessType(Type.Spigot);

        coreCommon.getCoreAPIProvider().setPluginMessageManager(pluginMessageManagerProvider);
    }

    private void registerListener() {
        new PlayerListener();
    }

    private void registerChannel() {
        mainClass.getServer().getMessenger().registerOutgoingPluginChannel(mainClass, "MineCode");
        mainClass.getServer().getMessenger().registerIncomingPluginChannel(mainClass, "MineCode", new PluginMessageListener());
    }

    public static CoreSpigot getInstance() {
        return instance;
    }

    public JavaPlugin getMainClass() {
        return mainClass;
    }
}