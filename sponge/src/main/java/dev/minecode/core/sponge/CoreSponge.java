package dev.minecode.core.sponge;

import dev.minecode.core.common.CoreCommon;

public class CoreSponge {

    private static CoreSponge instance;

    public CoreSponge(String pluginName, String pluginVersion) {
        instance = this;
        new CoreCommon(pluginName, pluginVersion);
    }

    public static CoreSponge getInstance() {
        return instance;
    }
}