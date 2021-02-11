package dev.minecode.core.api;

import dev.minecode.core.api.manager.*;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import net.md_5.bungee.api.chat.BaseComponent;

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


    // API-Manager
    public abstract DatabaseManager getDatabaseManager();

    public abstract FileManager getFileManager();

    public abstract LanguageManager getLanguageManager();

    public abstract ReplaceManager getReplaceManager(String message);

    public abstract ReplaceManager getReplaceManager(BaseComponent[] message);

    public abstract ReplaceManager getReplaceManager(Language language, LanguageAbstract path);

    public abstract UpdateManager getUpdateManager();


    // API-Objects
    public abstract CorePlayer getCorePlayer(int id);

    public abstract CorePlayer getCorePlayer(UUID uuid);

    public abstract CorePlayer getCorePlayer(String name);

    public abstract FileObject getFileObject(String fileName, String pluginName);

    public abstract FileObject getFileObject(String fileName, String pluginName, String... folders);

    public abstract Language getLanguage(String iso_code);


    // Variables
    public abstract String getDefaultLanguage();

    public abstract String getPluginName();

    public abstract boolean isUsingSQL();
}
