package dev.minecode.core.spigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.PluginPlattform;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.spigot.api.manager.PluginMessageManagerProvider;
import dev.minecode.core.spigot.api.manager.SQLPluginMessageManagerProvider;
import dev.minecode.core.spigot.listener.BukkitPluginMessageListener;
import dev.minecode.core.spigot.listener.PlayerListener;
import dev.minecode.core.spigot.listener.PluginListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreSpigot {
    private static CoreSpigot instance;
    private JavaPlugin mainClass;
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
        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL()) {
            CoreAPI.getInstance().setSQLPluginMessageManager(new SQLPluginMessageManagerProvider());
//            CoreAPI.getInstance().getSQLPluginMessageManager().startChecking();
        }
    }

    public void onDisable() {
        if (disabled) return;

        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL() && CoreAPI.getInstance().getSQLPluginMessageManager() != null)
            CoreAPI.getInstance().getSQLPluginMessageManager().cancelChecking();

        if (CoreAPI.getInstance().getDatabaseManager().isUsingSQL())
            CoreAPI.getInstance().getDatabaseManager().disconnect();
        else
            CoreAPI.getInstance().getFileManager().saveData();
        disabled = true;
    }

    public void registerPluginMessageChannel() {
        if (CoreAPI.getInstance().getNetworkManager().isMultiproxy()) return;

        Bukkit.getMessenger().registerOutgoingPluginChannel(mainClass, "BungeeCord");
        Bukkit.getMessenger().registerOutgoingPluginChannel(mainClass, "minecode:intern");
        Bukkit.getMessenger().registerOutgoingPluginChannel(mainClass, "minecode:pluginmessage");

        BukkitPluginMessageListener listener = new BukkitPluginMessageListener();
        Bukkit.getMessenger().registerIncomingPluginChannel(mainClass, "BungeeCord", listener);
        Bukkit.getMessenger().registerIncomingPluginChannel(mainClass, "minecode:intern", listener);
        Bukkit.getMessenger().registerIncomingPluginChannel(mainClass, "minecode:pluginmessage", listener);

    }

    public CorePlugin registerPlugin(JavaPlugin mainClass, boolean loadMessageFiles) {
        if (this.mainClass == null) {
            setMainClass(mainClass);
        }
        CorePlugin corePlugin = CoreAPI.getInstance().getPluginManager().registerPlugin(mainClass.getClass(), mainClass.getDescription().getName(), mainClass.getDescription().getVersion(), mainClass.getDataFolder(), PluginPlattform.SPIGOT, loadMessageFiles);
        CoreAPI.getInstance().getSQLPluginMessageManager().startChecking();
        return corePlugin;
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), mainClass);
        Bukkit.getPluginManager().registerEvents(new PluginListener(), mainClass);
    }

    public JavaPlugin getMainClass() {
        return mainClass;
    }

    public void setMainClass(JavaPlugin mainClass) {
        this.mainClass = mainClass;
        registerListeners();
        registerPluginMessageChannel();
    }
}
