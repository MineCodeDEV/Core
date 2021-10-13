package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.manager.FileManager;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.common.api.object.CorePluginProvider;
import dev.minecode.core.common.api.object.FileObjectProvider;
import org.spongepowered.configurate.serialize.TypeSerializer;

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
        CorePluginProvider tempCorePlugin = new CorePluginProvider(FileManagerProvider.class, "Core", "0.1.0-Pre.71", false);
        config = getFileObject(tempCorePlugin, "config.yml");
        players = getFileObject(tempCorePlugin, "players.yml");
    }

    @Override
    public boolean saveDatas() {
        return players.save();
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
    public FileObject getFileObject(CorePlugin corePlugin, String fileName, String... folders) {
        if (fileObjects.containsKey(corePlugin.getName() + fileName + Arrays.toString(folders)))
            return fileObjects.get(corePlugin.getName() + fileName + Arrays.toString(folders));

        FileObjectProvider fileObjectProvider = new FileObjectProvider(corePlugin, fileName, folders);
        fileObjects.put(corePlugin.getName() + fileName + Arrays.toString(folders), fileObjectProvider);
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

    @Override
    public FileObject getFileObject(CorePlugin corePlugin, String fileName, HashMap<Class, TypeSerializer> typeSerializers, String... folders) {
        if (fileObjects.containsKey(corePlugin.getName() + fileName + typeSerializers.toString() + Arrays.toString(folders)))
            return fileObjects.get(corePlugin.getName() + fileName + typeSerializers + Arrays.toString(folders));

        FileObjectProvider fileObjectProvider = new FileObjectProvider(corePlugin, fileName, typeSerializers, folders);
        fileObjects.put(corePlugin.getName() + fileName + typeSerializers + Arrays.toString(folders), fileObjectProvider);
        return fileObjectProvider;
    }

    @Override
    public FileObject getFileObject(CorePlugin corePlugin, String fileName, HashMap<Class, TypeSerializer> typeSerializers) {
        if (fileObjects.containsKey(corePlugin.getName() + fileName + typeSerializers.toString()))
            return fileObjects.get(corePlugin.getName() + fileName + typeSerializers);

        FileObjectProvider fileObjectProvider = new FileObjectProvider(corePlugin, fileName, typeSerializers);
        fileObjects.put(corePlugin.getName() + fileName + typeSerializers, fileObjectProvider);
        return fileObjectProvider;
    }
}
