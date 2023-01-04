package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.FileManager;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.common.api.object.FileObjectProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.util.Arrays;
import java.util.HashMap;

public class FileManagerProvider implements FileManager {

    private static final HashMap<String, FileObject> fileObjects = new HashMap<>();

    private final FileObject database, language, network, update;
    private FileObject players;

    public FileManagerProvider() {
        database = getFileObject(CoreAPI.getInstance().getCorePlugin(), "database.yml");
        language = getFileObject(CoreAPI.getInstance().getCorePlugin(), "language.yml");
        network = getFileObject(CoreAPI.getInstance().getCorePlugin(), "network.yml");
        update = getFileObject(CoreAPI.getInstance().getCorePlugin(), "update.yml");

        if (!database.getRoot().node("enable").getBoolean())
            players = getFileObject(CoreAPI.getInstance().getCorePlugin(), "players.yml");
    }

    @Override
    public boolean saveData() {
        if (!CoreAPI.getInstance().getDatabaseManager().isUsingSQL())
            return players.save();
        return false;
    }

    @Override
    public @NotNull FileObject getDatabase() {
        return database;
    }

    @Override
    public @NotNull FileObject getLanguage() {
        return language;
    }

    @Override
    public @NotNull FileObject getNetwork() {
        return network;
    }

    @Override
    public @NotNull FileObject getUpdate() {
        return update;
    }

    @Override
    public @Nullable FileObject getPlayers() {
        return players;
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
    public @NotNull FileObject getFileObject(@NotNull CorePlugin corePlugin, @NotNull String fileName, @NotNull HashMap<Class, TypeSerializer> typeSerializers, @NotNull String... folders) {
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
