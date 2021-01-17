package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.FileManager;
import dev.minecode.core.api.object.FileObject;
import org.spongepowered.configurate.ConfigurationNode;

import java.io.File;
import java.util.Map;

public class FileManagerProvider implements FileManager {

    private FileObject config;
    private FileObject players;

    public FileManagerProvider() {
        makeInstances();
        loadMessages();
    }

    private void makeInstances() {
        config = CoreAPI.getInstance().getFileObject("config.yml");
        players = CoreAPI.getInstance().getFileObject("players.yml");
    }

    private void loadMessages() {
        File messsageDirectory = new File("plugins/MineCode/" + CoreAPI.getInstance().getPluginName() + "/message/");
        messsageDirectory.mkdirs();
        for (Map.Entry<Object, ? extends ConfigurationNode> node : config.getConf().node("language", "languages").childrenMap().entrySet()) {
            CoreAPI.getInstance().getLanguage((String) node.getValue().key());
        }
    }

    @Override
    public void saveDatas() {
        players.save();
    }

    @Override
    public FileObject getPlayers() {
        return players;
    }

    @Override
    public FileObject getConfig() {
        return config;
    }
}
