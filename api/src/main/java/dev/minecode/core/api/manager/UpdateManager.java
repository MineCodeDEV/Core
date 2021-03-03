package dev.minecode.core.api.manager;

public interface UpdateManager {

    boolean updateAvailable();

    String getMatchingRelease();

    String getReleaseURL(String version);

    String getLatestFullRelease();

    String getLatestPreRelease();

    String getLatestRelease();

    boolean isUpdateNotification();

    boolean isUpdatePreReleases();

}
