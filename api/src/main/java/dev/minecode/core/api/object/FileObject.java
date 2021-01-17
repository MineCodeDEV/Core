package dev.minecode.core.api.object;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;

public interface FileObject {

    FileObject createFile();

    void loadConfig();

    void save();

    File getFile();

    String getDirectoryPath();

    String getFileName();

    String getFilePath();

    String getFileStreamPath();

    YamlConfigurationLoader getLoader();

    ConfigurationNode getConf();

    boolean isStream();

}
