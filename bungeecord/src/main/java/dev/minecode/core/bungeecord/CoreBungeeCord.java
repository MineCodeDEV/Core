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

public class CoreBungeeCord extends Plugin {
    private static CoreBungeeCord instance;

    public static CoreBungeeCord getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        makeInstances();
        registerListeners();
        registerPluginMessageChannel();
    }

    private void makeInstances() {
        instance = this;
        CoreCommon.getInstance();
        CoreAPI.getInstance().setPluginMessageManager(new PluginMessageManagerProvider());
        NetworkManager networkManager = CoreAPI.getInstance().getNetworkManager();

        if (!networkManager.isServernameSet() || !networkManager.isMultiproxy())
            networkManager.setServername("Proxy");

        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) {
            CoreAPI.getInstance().setSQLPluginMessageManager(new SQLPluginMessageManagerProvider());
            CoreAPI.getInstance().getSQLPluginMessageManager().startChecking();
        }
    }

    private void registerListeners() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, new BungeeCordListener());
    }

    private void registerPluginMessageChannel() {
        ProxyServer.getInstance().registerChannel("BungeeCord");
        ProxyServer.getInstance().registerChannel("minecode:pluginmessage");
    }

    @Override
    public void onDisable() {
        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL() && CoreAPI.getInstance().getSQLPluginMessageManager() != null)
            CoreAPI.getInstance().getSQLPluginMessageManager().cancelChecking();

        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
        else
            CoreAPI.getInstance().getFileManager().saveData();
    }

    public CorePlugin registerPlugin(Plugin mainClass, boolean loadMessageFiles) {
        return CoreAPI.getInstance().getPluginManager().registerPlugin(mainClass.getClass(), mainClass.getDescription().getName(), mainClass.getDescription().getVersion(), mainClass.getDataFolder(), PluginPlattform.BUNGEECORD, loadMessageFiles);
    }
}
