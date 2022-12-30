package dev.minecode.core.bungeecord;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.NetworkManager;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.PluginPlattform;
import dev.minecode.core.bungeecord.api.manager.PluginMessageManagerProvider;
import dev.minecode.core.bungeecord.listener.BungeeCordListener;
import dev.minecode.core.bungeecord.manager.ServerManager;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class CoreBungeeCord {
    private static CoreBungeeCord instance;

    private ServerManager serverManager;
    private boolean disabled;

    public CoreBungeeCord() {
        makeInstances();
        registerChannel();
    }

    public static CoreBungeeCord getInstance() {
        if (instance == null) new CoreBungeeCord();
        return instance;
    }

    private void makeInstances() {
        instance = this;
        serverManager = new ServerManager();
        CoreCommon.getInstance();
        CoreAPI.getInstance().setPluginMessageManager(new PluginMessageManagerProvider());
        NetworkManager networkManager = CoreAPI.getInstance().getNetworkManager();
        if (networkManager.getServername().equals("Service"))
            networkManager.setServername("Proxy");
        serverManager.sendServerNames();
    }

    private void registerChannel() {
        ProxyServer.getInstance().registerChannel("BungeeCord");
        ProxyServer.getInstance().registerChannel("minecode:pluginmessage");
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
        if (!BungeeCordListener.registered) {
            BungeeCordListener listener = new BungeeCordListener();
            ProxyServer.getInstance().getPluginManager().registerListener(mainClass, listener);
            BungeeCordListener.registered = true;
        }

        return CoreAPI.getInstance().getPluginManager().registerPlugin(mainClass.getClass(), mainClass.getDescription().getName(), mainClass.getDescription().getVersion(), mainClass.getDataFolder(), PluginPlattform.BUNGEECORD, loadMessageFiles);
    }

    public ServerManager getServerManager() {
        return serverManager;
    }
}
