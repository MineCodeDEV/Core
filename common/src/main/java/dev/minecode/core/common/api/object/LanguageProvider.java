package dev.minecode.core.common.api.object;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.HashMap;
import java.util.List;

public class LanguageProvider implements Language {

    private static HashMap<String, LanguageProvider> languages = new HashMap<>();

    private String isocode;
    private String name;
    private String displayname;
    private int slot;
    private List<String> lore;
    private String texture;

    private FileObject fileObject;
    private ConfigurationNode configurationNode;

    public LanguageProvider(String isocode) {
        languages.put(isocode, this);
        this.isocode = isocode;

        ConfigurationNode configNode = CoreAPI.getInstance().getFileManager().getConfig().getConf().node("language", "languages", this.isocode);
        this.fileObject = CoreAPI.getInstance().getFileObject(isocode + ".yml", CoreAPI.getInstance().getPluginName(), "message");
        this.configurationNode = fileObject.getConf();

        this.name = configNode.node("name").getString();
        this.displayname = configNode.node("displayname").getString();
        this.slot = configNode.node("slot").getInt();
        try {
            this.lore = configNode.node("lore").getList(String.class);
        } catch (SerializationException ignored) {
        }
        this.texture = configNode.node("texture").getString();
    }

    public static HashMap<String, LanguageProvider> getLanguages() {
        return languages;
    }

    @Override
    public String getIsocode() {
        return isocode;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayname() {
        return displayname;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public List<String> getLore() {
        return lore;
    }

    @Override
    public String getTexture() {
        return texture;
    }

    @Override
    public FileObject getFileObject() {
        return fileObject;
    }

    @Override
    public ConfigurationNode getConfigurationNode() {
        return configurationNode;
    }
}