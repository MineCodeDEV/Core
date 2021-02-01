package dev.minecode.core.common;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.common.api.CoreAPIProvider;
import dev.minecode.core.common.util.UUIDFetcher;

import java.util.UUID;

public class CoreCommon {
    private static CoreCommon instance;

    private UUIDFetcher uuidFetcher;
    private CorePlayer console;

    private String pluginName;
    private String pluginVersion;

    public CoreCommon(String pluginName, String pluginVersion) {
        this.pluginName = pluginName;
        this.pluginVersion = pluginVersion;
        makeInstances();
    }

    private void makeInstances() {
        instance = this;
        uuidFetcher = new UUIDFetcher();
        new CoreAPIProvider();

        console = CoreAPI.getInstance().getCorePlayer(new UUID(0, 0));
        /*
        UpdateManager updateManager = CoreAPI.getInstance().getUpdateManager();
        if (updateManager.updateAvailable()) {
            System.out.println("[" + pluginName + "] There is a newer Version available! You can download it at " + updateManager.getVersionURL(updateManager.getRecommendVersion()));
        }
        */
    }

    public static CoreCommon getInstance() {
        return instance;
    }

    public UUIDFetcher getUuidFetcher() {
        return uuidFetcher;
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
}
