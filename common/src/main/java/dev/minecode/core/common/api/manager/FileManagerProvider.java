package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.FileManager;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.common.api.object.FileObjectProvider;
import org.jetbrains.annotations.NotNull;
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
        config = getFileObject(CoreAPI.getInstance().getCorePlugin(), "config.yml");

        if (!config.getRoot().node("database", "enable").getBoolean())
            players = getFileObject(CoreAPI.getInstance().getCorePlugin(), "players.yml");
    }

    @Override
    public boolean saveData() {
        if (!CoreAPI.getInstance().getDatabaseManager().isUsingSQL())
            return players.save();
        return false;
    }

    @Override
    public @NotNull FileObject getPlayers() {
        return players;
    }

    @Override
    public @NotNull FileObject getConfig() {
        return config;
    }

    @Override
    public @NotNull FileObject getFileObject(@NotNull CorePlugin corePlugin, @NotNull String fileName, @NotNull String... folders) {
        if (fileObjects.containsKey(corePlugin.getName() + fileName + Arrays.toString(folders)))
            return fileObjects.get(corePlugin.getName() + fileName + Arrays.toString(folders));

        FileObjectProvider fileObjectProvider = new FileObjectProvider(corePlugin, fileName, folders);
        fileObjects.put(corePlugin.getName() + fileName + Arrays.toString(folders), fileObjectProvider);
        return fileObjectProvider;
    }

    @Override
    public @NotNull FileObject getFileObject(@NotNull CorePlugin corePlugin, @NotNull String fileName) {
        if (fileObjects.containsKey(corePlugin.getName() + fileName))
            return fileObjects.get(corePlugin.getName() + fileName);

        FileObjectProvider fileObjectProvider = new FileObjectProvider(corePlugin, fileName);
        fileObjects.put(corePlugin.getName() + fileName, fileObjectProvider);
        return fileObjectProvider;
    }

    @Override
    public @NotNull FileObject getFileObject(@NotNull CorePlugin corePlugin, @NotNull String fileName, @NotNull HashMap<Class, TypeSerializer> typeSerializers, String... folders) {
        if (fileObjects.containsKey(corePlugin.getName() + fileName + typeSerializers + Arrays.toString(folders)))
            return fileObjects.get(corePlugin.getName() + fileName + typeSerializers + Arrays.toString(folders));

        FileObjectProvider fileObjectProvider = new FileObjectProvider(corePlugin, fileName, typeSerializers, folders);
        fileObjects.put(corePlugin.getName() + fileName + typeSerializers + Arrays.toString(folders), fileObjectProvider);
        return fileObjectProvider;
    }

    @Override
    public @NotNull FileObject getFileObject(@NotNull CorePlugin corePlugin, @NotNull String fileName, @NotNull HashMap<Class, TypeSerializer> typeSerializers) {
        if (fileObjects.containsKey(corePlugin.getName() + fileName + typeSerializers))
            return fileObjects.get(corePlugin.getName() + fileName + typeSerializers);

        FileObjectProvider fileObjectProvider = new FileObjectProvider(corePlugin, fileName, typeSerializers);
        fileObjects.put(corePlugin.getName() + fileName + typeSerializers, fileObjectProvider);
        return fileObjectProvider;
    }
}
