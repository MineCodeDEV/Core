package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.util.HashMap;

public interface FileManager {

    boolean saveData();

    @Nullable FileObject getPlayers();

    @NotNull FileObject getConfig();

    @NotNull FileObject getFileObject(@NotNull CorePlugin corePlugin, @NotNull String fileName, @NotNull String... folders);

    @NotNull FileObject getFileObject(@NotNull CorePlugin corePlugin, @NotNull String fileName);

    @NotNull FileObject getFileObject(@NotNull CorePlugin corePlugin, @NotNull String fileName, @NotNull HashMap<Class, TypeSerializer> typeSerializers, @NotNull String... folders);

    @NotNull FileObject getFileObject(@NotNull CorePlugin corePlugin, @NotNull String fileName, @NotNull HashMap<Class, TypeSerializer> typeSerializers);
}
