package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.NetworkManager;
import dev.minecode.core.api.object.CloudPlattform;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;

public class NetworkManagerProvider implements NetworkManager {

    private final boolean enabled, multiproxy;
    private final CloudPlattform cloudPlattform;
    private String servername;

    private boolean servernameSet;

    public NetworkManagerProvider() {
        ConfigurationNode root = CoreAPI.getInstance().getFileManager().getNetwork().getRoot();

        enabled = root.node("enabled").getBoolean();
        multiproxy = root.node("multiproxy").getBoolean();
        servername = root.node("servername").getString("Service");
        cloudPlattform = CloudPlattform.valueOf(root.node("cloudname").getString("NONE"));
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isMultiproxy() {
        return multiproxy;
    }

    @Override
    public @NotNull String getServername() {
        return servername;
    }

    @Override
    public void setServername(String servername) {
        this.servernameSet = true;
        this.servername = servername;
    }

    @Override
    public boolean isServernameSet() {
        return servernameSet;
    }

    @Override
    public @NotNull CloudPlattform getCloudPlattform() {
        return cloudPlattform;
    }
}
