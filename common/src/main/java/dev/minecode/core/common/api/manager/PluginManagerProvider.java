package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.PluginManager;
import dev.minecode.core.common.CoreCommon;
import org.spongepowered.configurate.ConfigurationNode;

public class PluginManagerProvider implements PluginManager {

    private String pluginName, pluginVersion;
    private boolean usingSQL;

    public PluginManagerProvider() {
        makeInstances();
    }

    private void makeInstances() {
        pluginName = CoreCommon.getInstance().getPluginName();
        pluginVersion = CoreCommon.getInstance().getPluginVersion();

        ConfigurationNode conf = CoreAPI.getInstance().getFileManager().getConfig().getConf();
        usingSQL = conf.node("database", "enable").getBoolean();
    }

    @Override
    public String getPluginName() {
        return pluginName;
    }

    @Override
    public String getPluginVersion() {
        return pluginVersion;
    }

    @Override
    public boolean isUsingSQL() {
        return usingSQL;
    }
}
