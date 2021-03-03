package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.FileObject;

public interface FileManager {

    boolean saveDatas();

    FileObject getPlayers();

    FileObject getConfig();

    FileObject getFileObject(String fileName, String pluginName);

    FileObject getFileObject(String fileName, String pluginName, String... folders);
}
