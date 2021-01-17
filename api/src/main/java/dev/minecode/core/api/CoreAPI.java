package dev.minecode.core.api;

import dev.minecode.core.api.manager.*;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;

import java.io.InputStream;
import java.util.UUID;

public abstract class CoreAPI {

    // instance
    private static CoreAPI coreAPI;

    public static void setInstance(CoreAPI coreAPI) {
        CoreAPI.coreAPI = coreAPI;
    }

    public static CoreAPI getInstance() {
        return coreAPI;
    }

    public abstract void onDisable();

    public abstract String getPluginName();


    // API-Manager
    public abstract EventManager getEventManager();

    public abstract DatabaseManager getDatabaseManager();

    public abstract FileManager getFileManager();

    public abstract LanguageManager getLanguageManager();

    public abstract PluginMessageManager getPluginMessageManager();

    public abstract void setPluginMessageManager(PluginMessageManager pluginMessageManager);

    public abstract ReplaceManager getReplaceManager(String message);

    public abstract ReplaceManager getReplaceManager(String iso_code, String... path);

    public abstract UpdateManager getUpdateManager();


    // API-Objects
    public abstract CorePlayer getCorePlayer(int id);

    public abstract CorePlayer getCorePlayer(UUID uuid);

    public abstract CorePlayer getCorePlayer(String name);

    public abstract FileObject getFileObject(String filename);

    public abstract FileObject getFileObject(String filename, String folder);

    public abstract FileObject getFileObject(String filename, String folder, String... subFolders);

    public abstract Language getLanguage(String iso_code);


    // Variables
    public abstract boolean isUsingSQL();

    public abstract CorePlayer getConsole();

    public abstract String getDefaultLanguage();

    public abstract String getPluginMessageChannel();


    // Other staff
    public abstract InputStream getResourceAsStream(String fileName);


}
