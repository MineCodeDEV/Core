package dev.minecode.core.api.object;

import org.spongepowered.configurate.ConfigurationNode;

import java.util.List;

public interface Language {

    String getIsocode();

    String getName();

    String getDisplayname();

    int getSlot();

    List<String> getLore();

    String getTexture();

    FileObject getFileObject();

    ConfigurationNode getConfigurationNode();

}
