package dev.minecode.core.common.api.object;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.TypeSerializer;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FileObjectProvider implements FileObject {

    // files
    private final String fileStreamPath;
    private final File file;

    // MineCode
    private final CorePlugin corePlugin;

    // Configurate
    private YamlConfigurationLoader loader;
    private ConfigurationNode root;

    public FileObjectProvider(CorePlugin corePlugin, String fileName, String... folders) {
        StringBuilder foldersStringBuilder = new StringBuilder();
        for (String temp : folders)
            foldersStringBuilder.append(temp).append("/");

        this.corePlugin = corePlugin;
        this.fileStreamPath = corePlugin.getName() + "/" + foldersStringBuilder + fileName;
        this.file = new File(corePlugin.getDataFolder().getPath() + "/" + foldersStringBuilder, fileName);
        load();
    }

    public FileObjectProvider(CorePlugin corePlugin, String fileName) {
        this.corePlugin = corePlugin;
        this.fileStreamPath = corePlugin.getName() + "/" + fileName;
        this.file = new File(corePlugin.getDataFolder().getPath(), fileName);

        load();
    }

    public FileObjectProvider(CorePlugin corePlugin, String fileName, HashMap<Class, TypeSerializer> typeSerializers, String... folders) {
        StringBuilder foldersStringBuilder = new StringBuilder();
        for (String temp : folders)
            foldersStringBuilder.append(temp).append("/");

        this.corePlugin = corePlugin;
        this.fileStreamPath = corePlugin.getName() + "/" + foldersStringBuilder + fileName;
        this.file = new File(corePlugin.getDataFolder().getPath() + "/" + foldersStringBuilder, fileName);

        load(typeSerializers);
    }

    public FileObjectProvider(CorePlugin corePlugin, String fileName, HashMap<Class, TypeSerializer> typeSerializers) {
        this.corePlugin = corePlugin;
        this.fileStreamPath = corePlugin.getName() + "/" + fileName;
        this.file = new File(corePlugin.getDataFolder().getPath(), fileName);

        load(typeSerializers);
    }

    public void load() {
        if (!file.exists())
            createFile();
        reload();
    }

    public void load(HashMap<Class, TypeSerializer> typeSerializers) {
        if (!file.exists())
            createFile();
        reload(typeSerializers);
    }

    public boolean createFile() {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        InputStream inputStream = getResourceAsStream(fileStreamPath);
        if (inputStream != null) {
            try {
                Files.copy(inputStream, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        try {
            file.createNewFile();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean reload() {
        this.loader = YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).file(file).build();
        try {
            root = loader.load();
            return true;
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean reload(HashMap<Class, TypeSerializer> typeSerializers) {
        this.loader = YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).defaultOptions(configurationOptions -> configurationOptions.serializers(builder -> {
            for (Map.Entry<Class, TypeSerializer> serializers : typeSerializers.entrySet())
                builder.register(serializers.getKey(), serializers.getValue());
        })).file(file).build();
        try {
            root = loader.load();
            return true;
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            loader.save(root);
            return true;
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public @NotNull File getFile() {
        return file;
    }

    @Override
    public @NotNull YamlConfigurationLoader getLoader() {
        return loader;
    }

    @Override
    public @NotNull ConfigurationNode getRoot() {
        return root;
    }

    private InputStream getResourceAsStream(String fileName) {
        try {
            URL url = corePlugin.getMainClass().getClassLoader().getResource(fileName);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException e) {
            return null;
        }
    }
}
