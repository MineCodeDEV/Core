package dev.minecode.core.api.manager;

public interface UpdateManager {

    boolean updateAvailable();

    String getRecommendVersion();

    String getVersionURL(String version);

    String getLatestRelease();

    String getLatestPreRelease();

    boolean isRelease(String version);

    String getLatestVersion();

}
