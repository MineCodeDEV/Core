package dev.minecode.core.api;

import dev.minecode.core.api.manager.*;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CoreAPI {

    // Instance
    private static CoreAPI instance;

    @NotNull
    public static CoreAPI getInstance() {
        return instance;
    }

    public static void setInstance(@NotNull CoreAPI coreAPI) {
        CoreAPI.instance = coreAPI;
    }

    // Manager
    public abstract @NotNull DatabaseManager getDatabaseManager();

    public abstract @NotNull FileManager getFileManager();

    public abstract @NotNull LanguageManager getLanguageManager();

    public abstract @NotNull NetworkManager getNetworkManager();

    public abstract @NotNull PlayerManager getPlayerManager();

    public abstract @NotNull PluginManager getPluginManager();

    public abstract @Nullable PluginMessageManager getPluginMessageManager();

    public abstract void setPluginMessageManager(@NotNull PluginMessageManager pluginMessageManager);

    public abstract @NotNull ReplaceManager getReplaceManager(@NotNull String message);

    public abstract @NotNull ReplaceManager getReplaceManager(@NotNull BaseComponent[] message);

    public abstract @NotNull ReplaceManager getReplaceManager(@NotNull Language language, @NotNull String... path);

    public abstract @NotNull ReplaceManager getReplaceManager(@NotNull Language language, @NotNull LanguageAbstract path);

    public abstract @NotNull UpdateManager getUpdateManager(@NotNull CorePlugin corePlugin);

    /**
     * @Nullable if MySQL is disabled
     */
    public abstract @Nullable SQLPluginMessageManager getSQLPluginMessageManager();

    public abstract void setSQLPluginMessageManager(@NotNull SQLPluginMessageManager sqlPluginMessageManager);

    // Getter & Setter
    public abstract @NotNull CorePlugin getCorePlugin();
}
