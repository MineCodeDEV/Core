package dev.minecode.core.common;

import dev.minecode.core.common.api.CoreAPIProvider;
import dev.minecode.core.common.api.object.CorePlayerAddon;
import dev.minecode.core.common.util.UUIDFetcher;

public class CoreCommon {
    private static CoreCommon instance;

    private CorePlayerAddon corePlayerAddon;

    private UUIDFetcher uuidFetcher;

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

    public CorePlayerAddon getCorePlayerAddon() {
        return corePlayerAddon;
    }

    public void setCorePlayerAddon(CorePlayerAddon corePlayerAddon) {
        this.corePlayerAddon = corePlayerAddon;
    }

    public UUIDFetcher getUuidFetcher() {
        return uuidFetcher;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }
}
