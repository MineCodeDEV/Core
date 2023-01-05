package dev.minecode.core.spigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.PluginPlattform;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.api.manager.PluginMessageManagerProvider;
import dev.minecode.core.spigot.api.manager.SQLPluginMessageManagerProvider;
import dev.minecode.core.spigot.listener.BukkitPluginMessageListener;
import dev.minecode.core.spigot.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreSpigot extends JavaPlugin {
    private static CoreSpigot instance;

    public static CoreSpigot getInstance() {
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
        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) {
            CoreAPI.getInstance().setSQLPluginMessageManager(new SQLPluginMessageManagerProvider());
            CoreAPI.getInstance().getSQLPluginMessageManager().startChecking();
        }
    }

    private void registerPluginMessageChannel() {
        if (CoreAPI.getInstance().getNetworkManager().isMultiproxy()) return;

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "minecode:pluginmessage");

        BukkitPluginMessageListener listener = new BukkitPluginMessageListener();
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", listener);
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "minecode:pluginmessage", listener);

    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
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

    public CorePlugin registerPlugin(JavaPlugin mainClass, boolean loadMessageFiles) {
        return CoreAPI.getInstance().getPluginManager().registerPlugin(mainClass.getClass(), mainClass.getDescription().getName(), mainClass.getDescription().getVersion(), mainClass.getDataFolder(), PluginPlattform.SPIGOT, loadMessageFiles);
    }
}
