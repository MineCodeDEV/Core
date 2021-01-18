package dev.minecode.core.common.api;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.*;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.common.CoreCommon;
import dev.minecode.core.common.api.manager.*;
import dev.minecode.core.common.api.object.CorePlayerProvider;
import dev.minecode.core.common.api.object.FileObjectProvider;
import dev.minecode.core.common.api.object.LanguageProvider;

import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

public class CoreAPIProvider extends CoreAPI {

    private DatabaseManagerProvider databaseManagerProvider;
    private EventManagerProvider eventManagerProvider;
    private FileManagerProvider fileManagerProvider;
    private LanguageManagerProvider languageManagerProvider;
    private UpdateManagerProvider updateManagerProvider;

    private PluginMessageManager pluginMessageManager;

    public CoreAPIProvider() {
        makeInstances();
    }

    private void makeInstances() {
        CoreAPI.setInstance(this);
        databaseManagerProvider = new DatabaseManagerProvider();
        eventManagerProvider = new EventManagerProvider();
        fileManagerProvider = new FileManagerProvider();
        languageManagerProvider = new LanguageManagerProvider();
        updateManagerProvider = new UpdateManagerProvider();
    }

    @Override
    public void onDisable() {
        getFileManager().saveDatas();
        if (CoreCommon.getInstance().isUsingSQL()) getDatabaseManager().disconnect();
    }

    @Override
    public String getPluginName() {
        return CoreCommon.getInstance().getPluginName();
    }


    // API-Manager
    @Override
    public EventManager getEventManager() {
        return eventManagerProvider;
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManagerProvider;
    }

    @Override
    public FileManager getFileManager() {
        return fileManagerProvider;
    }

    @Override
    public PluginMessageManager getPluginMessageManager() {
        return pluginMessageManager;
    }

    @Override
    public void setPluginMessageManager(PluginMessageManager pluginMessageManager) {
        this.pluginMessageManager = pluginMessageManager;
    }

    @Override
    public LanguageManager getLanguageManager() {
        return languageManagerProvider;
    }

    @Override
    public ReplaceManager getReplaceManager(String message) {
        return new ReplaceManagerProvider(message);
    }

    @Override
    public ReplaceManager getReplaceManager(String iso_code, String... path) {
        return new ReplaceManagerProvider(iso_code, path);
    }

    @Override
    public UpdateManager getUpdateManager() {
        return updateManagerProvider;
    }


    // API-Objects
    @Override
    public CorePlayer getCorePlayer(int id) {
        if (CorePlayerProvider.getIdCache().containsKey(id)) {
            CorePlayerProvider corePlayerProvider = CorePlayerProvider.getIdCache().get(id);
            corePlayerProvider.load();
            return corePlayerProvider;
        }

        CorePlayerProvider corePlayerProvider = new CorePlayerProvider(id);
        CorePlayerProvider.getIdCache().put(id, corePlayerProvider);
        CorePlayerProvider.getUuidCache().put(corePlayerProvider.getName(), corePlayerProvider);
        CorePlayerProvider.getNameCache().put(corePlayerProvider.getUuid(), corePlayerProvider);
        return corePlayerProvider;
    }

    @Override
    public CorePlayer getCorePlayer(UUID uuid) {
        if (CorePlayerProvider.getNameCache().containsKey(uuid)) {
            CorePlayerProvider corePlayerProvider = CorePlayerProvider.getNameCache().get(uuid);
            corePlayerProvider.load();
            return corePlayerProvider;
        }

        CorePlayerProvider corePlayerProvider = new CorePlayerProvider(uuid);
        CorePlayerProvider.getIdCache().put(corePlayerProvider.getID(), corePlayerProvider);
        CorePlayerProvider.getUuidCache().put(corePlayerProvider.getName(), corePlayerProvider);
        CorePlayerProvider.getNameCache().put(uuid, corePlayerProvider);
        return corePlayerProvider;
    }

    @Override
    public CorePlayer getCorePlayer(String name) {
        if (CorePlayerProvider.getUuidCache().containsKey(name)) {
            CorePlayerProvider corePlayerProvider = CorePlayerProvider.getUuidCache().get(name);
            corePlayerProvider.load();
            return corePlayerProvider;
        }

        CorePlayerProvider corePlayerProvider = new CorePlayerProvider(name);
        CorePlayerProvider.getIdCache().put(corePlayerProvider.getID(), corePlayerProvider);
        CorePlayerProvider.getUuidCache().put(name, corePlayerProvider);
        CorePlayerProvider.getNameCache().put(corePlayerProvider.getUuid(), corePlayerProvider);
        return corePlayerProvider;
    }

    @Override
    public FileObject getFileObject(String filename) {
        if (FileObjectProvider.getFileObjects().containsKey(filename))
            return FileObjectProvider.getFileObjects().get(filename);
        return new FileObjectProvider(filename);
    }

    @Override
    public FileObject getFileObject(String filename, String folder) {
        if (FileObjectProvider.getFileObjects().containsKey(filename + folder))
            return FileObjectProvider.getFileObjects().get(filename);
        return new FileObjectProvider(filename, folder);
    }

    @Override
    public FileObject getFileObject(String filename, String folder, String... subFolders) {
        if (FileObjectProvider.getFileObjects().containsKey(filename + folder + Arrays.toString(subFolders)))
            return FileObjectProvider.getFileObjects().get(filename);
        return new FileObjectProvider(filename, folder, subFolders);
    }

    @Override
    public Language getLanguage(String iso_code) {
        if (LanguageProvider.getLanguages().containsKey(iso_code))
            return LanguageProvider.getLanguages().get(iso_code);
        return LanguageProvider.getLanguages().get(iso_code);
    }


    // Variables
    @Override
    public CorePlayer getConsole() {
        return CoreCommon.getInstance().getConsole();
    }

    @Override
    public boolean isUsingSQL() {
        return CoreCommon.getInstance().isUsingSQL();
    }

    @Override
    public String getDefaultLanguage() {
        return CoreCommon.getInstance().getDefaultLanguage();
    }

    @Override
    public String getPluginMessageChannel() {
        return "MineCode";
    }

    // Other staff
    @Override
    public InputStream getResourceAsStream(String fileName) {
        return CoreCommon.getInstance().getResourceAsStream(fileName);
    }


}
