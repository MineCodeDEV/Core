package dev.minecode.core.bungeecord;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.NetworkManager;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.PluginPlattform;
import dev.minecode.core.bungeecord.api.manager.PluginMessageManagerProvider;
import dev.minecode.core.bungeecord.api.manager.SQLPluginMessageManagerProvider;
import dev.minecode.core.bungeecord.listener.BungeeCordListener;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungeeCord {
    private static CoreBungeeCord instance;

    private Plugin mainClass;

    private boolean disabled;

    public CoreBungeeCord() {
        makeInstances();
        registerPluginMessageChannel();
//        CoreAPI.getInstance().getSQLPluginMessageManager().startChecking();
    }

    public static CoreBungeeCord getInstance() {
        if (instance == null) new CoreBungeeCord();
        return instance;
    }

    private void makeInstances() {
        instance = this;
        CoreCommon.getInstance();
        CoreAPI.getInstance().setPluginMessageManager(new PluginMessageManagerProvider());
        NetworkManager networkManager = CoreAPI.getInstance().getNetworkManager();
        if (!networkManager.isServernameSet() || !networkManager.isMultiproxy()) {
            networkManager.setServername("Proxy");
        }
        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) {
            CoreAPI.getInstance().setSQLPluginMessageManager(new SQLPluginMessageManagerProvider());
        }
    }

    private void registerPluginMessageChannel() {
        ProxyServer.getInstance().registerChannel("BungeeCord");
        ProxyServer.getInstance().registerChannel("minecode:pluginmessage");
    }

    private void registerListeners() {
        ProxyServer.getInstance().getPluginManager().registerListener(mainClass, new BungeeCordListener());
    }

    public void onDisable() {
        if (disabled) return;

        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
        else
            CoreAPI.getInstance().getFileManager().saveData();
        disabled = true;
    }

    public CorePlugin registerPlugin(Plugin mainClass, boolean loadMessageFiles) {
        if (this.mainClass == null) setMainClass(mainClass);

        CorePlugin corePlugin = CoreAPI.getInstance().getPluginManager().registerPlugin(mainClass.getClass(), mainClass.getDescription().getName(), mainClass.getDescription().getVersion(), mainClass.getDataFolder(), PluginPlattform.BUNGEECORD, loadMessageFiles);
        CoreAPI.getInstance().getSQLPluginMessageManager().startChecking();
        return corePlugin;
    }

    public Plugin getMainClass() {
        return mainClass;
    }

    public void setMainClass(Plugin mainClass) {
        this.mainClass = mainClass;
        registerListeners();
        registerPluginMessageChannel();
    }

}
