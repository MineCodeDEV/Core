package dev.minecode.core.bungeecord;

import dev.minecode.core.api.object.Type;
import dev.minecode.core.bungeecord.api.manager.PluginMessageManagerProvider;
import dev.minecode.core.bungeecord.listener.PluginMessageListener;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungeeCord {
    private static CoreBungeeCord instance;

    private CoreCommon coreCommon;
    private Plugin mainClass;

    private PluginMessageManagerProvider pluginMessageManagerProvider;

    private String pluginName;
    private String pluginVersion;

    public CoreBungeeCord(String pluginName, String pluginVersion, Plugin mainClass) {
        this.pluginName = pluginName;
        this.pluginVersion = pluginVersion;
        this.mainClass = mainClass;
        CoreCommon.getInstance().setProcessName("BungeeCord");
        CoreCommon.getInstance().setProcessType(Type.BungeeCord);

        CoreCommon.getInstance().getPluginMessageManager().runSQLChecker();

        makeInstances();
        registerChannel();
        registerListener();
    }

    private void makeInstances() {
        instance = this;
        pluginMessageManagerProvider = new PluginMessageManagerProvider();
        coreCommon = new CoreCommon(pluginName, pluginVersion);

        coreCommon.getCoreAPIProvider().setPluginMessageManager(pluginMessageManagerProvider);
    }

    private void registerChannel() {
        mainClass.getProxy().registerChannel("MineCode");
    }

    private void registerListener() {
        new PluginMessageListener();
    }

    public static CoreBungeeCord getInstance() {
        return instance;
    }

    public Plugin getMainClass() {
        return mainClass;
    }
}
