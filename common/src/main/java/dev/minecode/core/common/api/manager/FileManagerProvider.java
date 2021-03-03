package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.manager.FileManager;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.common.api.object.FileObjectProvider;

import java.util.Arrays;
import java.util.HashMap;

public class FileManagerProvider implements FileManager {

    private static HashMap<String, FileObject> fileObjects = new HashMap<>();

    private FileObject config;
    private FileObject players;

    public FileManagerProvider() {
        makeInstances();
    }

    private void makeInstances() {
        config = getFileObject("config.yml", "Core");
        players = getFileObject("players.yml", "Core");
    }

    @Override
    public boolean saveDatas() {
        boolean saved = true;
        if (!players.save())
            saved = false;
        return saved;
    }

    @Override
    public FileObject getPlayers() {
        return players;
    }

    @Override
    public FileObject getConfig() {
        return config;
    }

    @Override
    public FileObject getFileObject(String fileName, String pluginName, String... folders) {
        if (fileObjects.containsKey(fileName + pluginName + Arrays.toString(folders)))
            return fileObjects.get(fileName + pluginName + Arrays.toString(folders));

        FileObjectProvider fileObjectProvider = new FileObjectProvider(fileName, pluginName, folders);
        fileObjects.put(fileName + pluginName + Arrays.toString(folders), fileObjectProvider);
        return fileObjectProvider;
    }

    @Override
    public FileObject getFileObject(String fileName, String pluginName) {
        if (fileObjects.containsKey(fileName + pluginName))
            return fileObjects.get(fileName + pluginName);

        FileObjectProvider fileObjectProvider = new FileObjectProvider(fileName, pluginName);
        fileObjects.put(fileName + pluginName, fileObjectProvider);
        return fileObjectProvider;
    }
}
