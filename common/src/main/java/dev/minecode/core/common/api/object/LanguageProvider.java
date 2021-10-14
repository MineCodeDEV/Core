package dev.minecode.core.common.api.object;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.common.api.manager.LanguageManagerProvider;
import org.spongepowered.configurate.ConfigurationNode;

public class LanguageProvider implements Language {

    private final CorePlugin corePlugin;
    private final String isocode;
    private final String name;
    private final String displayname;
    private final FileObject fileObject;

    public LanguageProvider(CorePlugin corePlugin, String isocode) {
        LanguageManagerProvider.getLanguages().add(this);
        this.corePlugin = corePlugin;
        this.isocode = isocode;

        ConfigurationNode configNode = CoreAPI.getInstance().getFileManager().getConfig().getConf().node("language", "languages", this.isocode);
        this.fileObject = CoreAPI.getInstance().getFileManager().getFileObject(corePlugin, isocode + ".yml", "message");

        this.name = configNode.node("name").getString();
        this.displayname = configNode.node("displayname").getString();
    }

    @Override
    public CorePlugin getPlugin() {
        return corePlugin;
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
    public FileObject getFileObject() {
        return fileObject;
    }
}