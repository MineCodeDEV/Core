package dev.minecode.core.api.object;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.TypeSerializer;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.util.HashMap;

public interface FileObject {

    boolean reload();

    boolean reload(HashMap<Class, TypeSerializer> typeSerializers);

    boolean save();

    @NotNull File getFile();

    @NotNull YamlConfigurationLoader getLoader();

    @NotNull ConfigurationNode getRoot();

}
