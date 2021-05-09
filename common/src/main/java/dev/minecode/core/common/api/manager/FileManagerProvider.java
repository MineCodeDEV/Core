package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.FileManager;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.common.api.object.FileObjectProvider;

import java.util.Arrays;
import java.util.HashMap;

public class FileManagerProvider implements FileManager {

    private static final HashMap<String, FileObject> fileObjects = new HashMap<>();

    private FileObject config;
    private FileObject players;

    public FileManagerProvider() {
        makeInstances();
    }

    private void makeInstances() {
        config = getFileObject(CoreAPI.getInstance().getThisCorePlugin(), "config.yml");
        players = getFileObject(CoreAPI.getInstance().getThisCorePlugin(), "players.yml");
    }

    @Override
    public boolean saveDatas() {
        boolean saved = players.save();
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
    public FileObject getFileObject(CorePlugin corePluign, String fileName, String... folders) {
        if (fileObjects.containsKey(corePluign.getName() + fileName + Arrays.toString(folders)))
            return fileObjects.get(corePluign.getName() + fileName + Arrays.toString(folders));

        FileObjectProvider fileObjectProvider = new FileObjectProvider(corePluign, fileName, folders);
        fileObjects.put(corePluign.getName() + fileName + Arrays.toString(folders), fileObjectProvider);
        return fileObjectProvider;
    }

    @Override
    public FileObject getFileObject(CorePlugin corePlugin, String fileName) {
        if (fileObjects.containsKey(corePlugin.getName() + fileName))
            return fileObjects.get(corePlugin.getName() + fileName);

        FileObjectProvider fileObjectProvider = new FileObjectProvider(corePlugin, fileName);
        fileObjects.put(corePlugin.getName() + fileName, fileObjectProvider);
        return fileObjectProvider;
    }
}
