package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.FileObject;

public interface FileManager {

    void saveDatas();

    FileObject getPlayers();

    FileObject getConfig();

}
