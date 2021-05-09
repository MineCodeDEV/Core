package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;

public interface FileManager {

    boolean saveDatas();

    FileObject getPlayers();

    FileObject getConfig();

    FileObject getFileObject(CorePlugin corePlugin, String fileName);

    FileObject getFileObject(CorePlugin corePlugin, String fileName, String... folders);
}
