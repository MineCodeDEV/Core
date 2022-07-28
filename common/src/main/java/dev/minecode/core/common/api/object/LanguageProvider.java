package dev.minecode.core.common.api.object;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;

public class LanguageProvider implements Language {

    private final CorePlugin corePlugin;
    private final String isocode;
    private final String name;
    private final String displayname;
    private final FileObject fileObject;

    public LanguageProvider(CorePlugin corePlugin, String isocode) {
        this.corePlugin = corePlugin;
        this.isocode = isocode;

        ConfigurationNode configNode = CoreAPI.getInstance().getFileManager().getConfig().getRoot().node("language", "languages", this.isocode);
        this.fileObject = CoreAPI.getInstance().getFileManager().getFileObject(corePlugin, isocode + ".yml", "message");

        this.name = configNode.node("name").getString();
        this.displayname = configNode.node("displayname").getString();
    }

    @Override
    public @NotNull CorePlugin getPlugin() {
        return corePlugin;
    }

    @Override
    public @NotNull String getIsocode() {
        return isocode;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String getDisplayname() {
        return displayname;
    }

    @Override
    public @NotNull FileObject getFileObject() {
        return fileObject;
    }
}