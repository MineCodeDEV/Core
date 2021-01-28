package dev.minecode.core.common;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.UpdateManager;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.Type;
import dev.minecode.core.common.api.CoreAPIProvider;
import dev.minecode.core.common.manager.PluginMessageManager;
import dev.minecode.core.common.util.UUIDFetcher;
import org.spongepowered.configurate.ConfigurationNode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class CoreCommon {

    private static CoreCommon instance;

    private CoreAPIProvider coreAPIProvider;
    private UUIDFetcher uuidFetcher;
    private PluginMessageManager pluginMessageManager;
    private CorePlayer console;

    private String pluginName;
    private String pluginVersion;
    private String processName;
    private Type processType;
    private boolean usingSQL;
    private String defaultLanguage;


    public CoreCommon(String pluginName, String pluginVersion) {
        this.pluginName = pluginName;
        this.pluginVersion = pluginVersion;
        makeInstances();
    }

    private void makeInstances() {
        instance = this;
        coreAPIProvider = new CoreAPIProvider();
        uuidFetcher = new UUIDFetcher();

        ConfigurationNode configNode = coreAPIProvider.getFileManager().getConfig().getConf();
        usingSQL = configNode.node("database", "enable").getBoolean();
        defaultLanguage = configNode.node("language", "default").getString();

        pluginMessageManager = new PluginMessageManager();
        console = CoreAPI.getInstance().getCorePlayer(new UUID(0, 0));

        UpdateManager updateManager = CoreAPI.getInstance().getUpdateManager();

        if (updateManager.updateAvailable()) {
            System.out.println("[" + pluginName + "] There is a newer Version available! You can download it at " + updateManager.getVersionURL(updateManager.getRecommendVersion()));
        }
    }

    public PluginMessageManager getPluginMessageManager() {
        return pluginMessageManager;
    }

    public CorePlayer getConsole() {
        return console;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public boolean isUsingSQL() {
        return usingSQL;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public static CoreCommon getInstance() {
        return instance;
    }

    public UUIDFetcher getUuidFetcher() {
        return uuidFetcher;
    }

    public InputStream getResourceAsStream(String fileName) {
        try {
            URL url = this.getClass().getClassLoader().getResource(fileName);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException var4) {
            return null;
        }
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Type getProcessType() {
        return processType;
    }

    public void setProcessType(Type processType) {
        this.processType = processType;
    }

    public CoreAPIProvider getCoreAPIProvider() {
        return coreAPIProvider;
    }
}
