package dev.minecode.core.api.object;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;

public interface FileObject {

    boolean reload();

    boolean save();

    File getFile();

    YamlConfigurationLoader getLoader();

    ConfigurationNode getConf();

    boolean isStream();

}
