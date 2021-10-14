package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.util.HashMap;

public interface FileManager {

    boolean saveData();

    FileObject getPlayers();

    FileObject getConfig();

    FileObject getFileObject(CorePlugin corePlugin, String fileName, String... folders);

    FileObject getFileObject(CorePlugin corePlugin, String fileName);

    FileObject getFileObject(CorePlugin corePlugin, String fileName, HashMap<Class, TypeSerializer> typeSerializers, String... folders);

    FileObject getFileObject(CorePlugin corePlugin, String fileName, HashMap<Class, TypeSerializer> typeSerializers);
}
