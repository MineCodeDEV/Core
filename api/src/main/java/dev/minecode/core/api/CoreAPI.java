package dev.minecode.core.api;

import dev.minecode.core.api.manager.*;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import net.md_5.bungee.api.chat.BaseComponent;

public abstract class CoreAPI {

    // Instance
    private static CoreAPI instance;

    public static CoreAPI getInstance() {
        return instance;
    }

    public static void setInstance(CoreAPI coreAPI) {
        CoreAPI.instance = coreAPI;
    }

    // Manager
    public abstract DatabaseManager getDatabaseManager();

    public abstract FileManager getFileManager();

    public abstract LanguageManager getLanguageManager();

    public abstract PlayerManager getPlayerManager();

    public abstract PluginManager getPluginManager();

    public abstract ReplaceManager getReplaceManager(String message);

    public abstract ReplaceManager getReplaceManager(BaseComponent[] message);

    public abstract ReplaceManager getReplaceManager(Language language, String... path);

    public abstract ReplaceManager getReplaceManager(Language language, LanguageAbstract path);

    public abstract UpdateManager getUpdateManager(CorePlugin corePlugin);

    // Getter & Setter
    public abstract CorePlugin getThisCorePlugin();

    public abstract boolean isUsingSQL();
}
