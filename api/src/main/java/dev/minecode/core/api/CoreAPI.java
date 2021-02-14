package dev.minecode.core.api;

import dev.minecode.core.api.manager.*;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.List;
import java.util.UUID;

public abstract class CoreAPI {

    // Instance
    private static CoreAPI coreAPI;

    public static CoreAPI getInstance() {
        return coreAPI;
    }

    public static void setInstance(CoreAPI coreAPI) {
        CoreAPI.coreAPI = coreAPI;
    }

    // Manager
    public abstract DatabaseManager getDatabaseManager();

    public abstract FileManager getFileManager();

    public abstract LanguageManager getLanguageManager();

    public abstract ReplaceManager getReplaceManager(String message);

    public abstract ReplaceManager getReplaceManager(BaseComponent[] message);

    public abstract ReplaceManager getReplaceManager(Language language, LanguageAbstract path);

    public abstract UpdateManager getUpdateManager();


    // Objects
    public abstract CorePlayer getCorePlayer(int id);

    public abstract CorePlayer getCorePlayer(UUID uuid);

    public abstract CorePlayer getCorePlayer(String name);

    public abstract FileObject getFileObject(String fileName, String pluginName);

    public abstract FileObject getFileObject(String fileName, String pluginName, String... folders);

    public abstract Language getLanguage(String isocode);

    public abstract List<Language> getLanguages();

    public abstract Language getDefaultLanguage();


    // Variables
    public abstract String getPluginName();

    public abstract String getPluginVersion();

    public abstract boolean isUsingSQL();
}
