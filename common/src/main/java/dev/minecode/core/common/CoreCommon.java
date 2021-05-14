package dev.minecode.core.common;

import dev.minecode.core.common.api.CoreAPIProvider;
import dev.minecode.core.common.api.manager.PlayerManagerProvider;
import dev.minecode.core.common.util.UUIDFetcher;

public class CoreCommon {
    private static CoreCommon instance;

    private PlayerManagerProvider playerManagerProvider;
    private UUIDFetcher uuidFetcher;

    public CoreCommon() {
        makeInstances();
    }

    public static CoreCommon getInstance() {
        if (instance == null) new CoreCommon();
        return instance;
    }

    private void makeInstances() {
        instance = this;
        uuidFetcher = new UUIDFetcher();
        new CoreAPIProvider();
    }

    public PlayerManagerProvider getPlayerManagerProvider() {
        return playerManagerProvider;
    }

    public void setPlayerManagerProvider(PlayerManagerProvider playerManagerProvider) {
        this.playerManagerProvider = playerManagerProvider;
    }

    public UUIDFetcher getUuidFetcher() {
        return uuidFetcher;
    }

}
