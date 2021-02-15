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

    public static CoreCommon getInstance() {
        return instance;
    }

    private void makeInstances() {
        instance = this;
        uuidFetcher = new UUIDFetcher();
        new CoreAPIProvider();
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
