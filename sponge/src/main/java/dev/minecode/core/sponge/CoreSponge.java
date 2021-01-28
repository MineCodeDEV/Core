package dev.minecode.core.sponge;

import dev.minecode.core.api.object.Type;
import dev.minecode.core.common.CoreCommon;

public class CoreSponge {
    private static CoreSponge instance;

    private CoreCommon coreCommon;

    private String pluginName;
    private String pluginVersion;

    public CoreSponge(String pluginName, String pluginVersion) {
        this.pluginName = pluginName;
        this.pluginVersion = pluginVersion;

        makeInstances();
    }

    private void makeInstances() {
        instance = this;
        coreCommon = new CoreCommon(pluginName, pluginVersion);
        // CoreCommon.getInstance().setProcessName(Sponge.getServer().getName());
        CoreCommon.getInstance().setProcessType(Type.Sponge);
    }

    public static CoreSponge getInstance() {
        return instance;
    }
}