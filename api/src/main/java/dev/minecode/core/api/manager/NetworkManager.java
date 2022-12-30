package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CloudPlattform;
import org.jetbrains.annotations.NotNull;

public interface NetworkManager {

    boolean isEnabled();

    boolean isMultiproxy();

    @NotNull String getServername();

    void setServername(String servername);

    @NotNull CloudPlattform getCloudPlattform();

}
