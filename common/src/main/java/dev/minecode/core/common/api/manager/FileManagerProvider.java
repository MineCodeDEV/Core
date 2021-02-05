package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.FileManager;
import dev.minecode.core.api.object.FileObject;

public class FileManagerProvider implements FileManager {

    private FileObject config;
    private FileObject players;

    public FileManagerProvider() {
        makeInstances();
    }

    private void makeInstances() {
        config = CoreAPI.getInstance().getFileObject("config.yml", "Core");
        players = CoreAPI.getInstance().getFileObject("players.yml", "Core");
    }

    @Override
    public void saveDatas() {
        players.save();
    }

    @Override
    public FileObject getPlayers() {
        return players;
    }

    @Override
    public FileObject getConfig() {
        return config;
    }
}
