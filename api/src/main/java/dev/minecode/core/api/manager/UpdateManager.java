package dev.minecode.core.api.manager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UpdateManager {

    boolean checkUpdateAvailable();

    @Nullable String getMatchingRelease();

    @Nullable String getReleaseURL(@NotNull String version);

    @Nullable String getLatestFullRelease();

    @Nullable String getLatestPreRelease();

    @Nullable String getLatestRelease();

    boolean isUpdateNotification();

    boolean isUpdatePreReleases();

}
