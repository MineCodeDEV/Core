package dev.minecode.core.spigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.NetworkManager;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.PluginPlattform;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.listener.BukkitPluginMessageListener;
import dev.minecode.core.spigot.manager.PluginMessageManagerProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreSpigot {
    private static CoreSpigot instance;
    private boolean disabled;

    public CoreSpigot() {
        makeInstances();
    }

    public static CoreSpigot getInstance() {
        if (instance == null) new CoreSpigot();
        return instance;
    }

    private void makeInstances() {
        instance = this;
        disabled = false;
        CoreCommon.getInstance();

        CoreAPI.getInstance().setPluginMessageManager(new PluginMessageManagerProvider());
        NetworkManager networkManager = CoreAPI.getInstance().getNetworkManager();
        if (networkManager.getServername().equals("Service"))
            if (!Bukkit.getName().isEmpty() && !Bukkit.getName().isBlank())
                networkManager.setServername(Bukkit.getName());
    }

    public void onDisable() {
        if (disabled) return;

        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
        else
            CoreAPI.getInstance().getFileManager().saveData();
        disabled = true;
    }

    public CorePlugin registerPlugin(JavaPlugin mainClass, boolean loadMessageFiles) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(mainClass, "BungeeCord");
        Bukkit.getMessenger().registerOutgoingPluginChannel(mainClass, "minecode:intern");
        Bukkit.getMessenger().registerOutgoingPluginChannel(mainClass, "minecode:pluginmessage");

        if (!BukkitPluginMessageListener.registered) {
            BukkitPluginMessageListener listener = new BukkitPluginMessageListener();
            Bukkit.getMessenger().registerIncomingPluginChannel(mainClass, "minecode:intern", listener);
            Bukkit.getMessenger().registerIncomingPluginChannel(mainClass, "minecode:pluginmessage", listener);
            BukkitPluginMessageListener.registered = true;
        }

        return CoreAPI.getInstance().getPluginManager().registerPlugin(mainClass.getClass(), mainClass.getDescription().getName(), mainClass.getDescription().getVersion(), mainClass.getDataFolder(), PluginPlattform.SPIGOT, loadMessageFiles);
    }
}
