package dev.minecode.core.common.api.object;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.api.object.FileObject;
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

    // directories
    private static final String pluginsDirectoryPath = "plugins/";
    private final String pluginDirectoryPath;
    private final String fileDirectoryPath;

    // file
    private final String fileStreamPath;
    private final File file;

    // other
    private final CorePlugin corePlugin;

    // Configurate
    private YamlConfigurationLoader loader;
    private ConfigurationNode conf;
    private boolean stream;

    public FileObjectProvider(CorePlugin corePlugin, String fileName, String... folders) {
        System.out.println("1: " + fileName);
        this.corePlugin = corePlugin;
        this.pluginDirectoryPath = pluginsDirectoryPath + corePlugin.getName() + "/";

        StringBuilder foldersStringBuilder = new StringBuilder();
        for (String temp : folders)
            foldersStringBuilder.append(temp).append("/");

        this.fileDirectoryPath = pluginDirectoryPath + foldersStringBuilder + "/";
        this.fileStreamPath = corePlugin.getName() + "/" + foldersStringBuilder + fileName;
        this.file = new File(fileDirectoryPath, fileName);
        load();
    }

    public FileObjectProvider(CorePlugin corePlugin, String fileName) {
        System.out.println("2: " + fileName);
        this.corePlugin = corePlugin;
        this.pluginDirectoryPath = pluginsDirectoryPath + corePlugin.getName() + "/";

        this.fileDirectoryPath = pluginDirectoryPath;
        this.fileStreamPath = corePlugin.getName() + "/" + fileName;
        this.file = new File(fileDirectoryPath, fileName);
        load();
    }

    public FileObjectProvider(CorePlugin corePlugin, String fileName, HashMap<Class, TypeSerializer> typeSerializers, String... folders) {
        System.out.println("3: " + fileName);
        this.corePlugin = corePlugin;
        this.pluginDirectoryPath = pluginsDirectoryPath + corePlugin.getName() + "/";

        StringBuilder foldersStringBuilder = new StringBuilder();
        for (String temp : folders)
            foldersStringBuilder.append(temp).append("/");

        this.fileDirectoryPath = pluginDirectoryPath + foldersStringBuilder + "/";
        this.fileStreamPath = corePlugin.getName() + "/" + foldersStringBuilder + fileName;
        this.file = new File(fileDirectoryPath, fileName);
        load(typeSerializers);
    }

    public FileObjectProvider(CorePlugin corePlugin, String fileName, HashMap<Class, TypeSerializer> typeSerializers) {
        System.out.println("4: " + fileName);
        this.corePlugin = corePlugin;
        this.pluginDirectoryPath = pluginsDirectoryPath + corePlugin.getName() + "/";

        this.fileDirectoryPath = pluginDirectoryPath;
        this.fileStreamPath = corePlugin.getName() + "/" + fileName;
        this.file = new File(fileDirectoryPath, fileName);
        load(typeSerializers);
    }

    public boolean load() {
        boolean success = true;
        if (!file.exists())
            success = createFile();

        if (success) return reload();
        return false;
    }

    public boolean load(HashMap<Class, TypeSerializer> typeSerializers) {
        boolean success = true;
        if (!file.exists())
            success = createFile();

        if (success) return reload(typeSerializers);
        return false;
    }

    public boolean createFile() {
        new File(fileDirectoryPath).mkdirs();

        InputStream inputStream = getResourceAsStream(fileStreamPath);
        if (inputStream != null) {
            try {
                Files.copy(inputStream, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            stream = true;
            return true;
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        stream = false;
        return true;
    }

    @Override
    public boolean reload() {
        this.loader = YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).file(file).build();
        try {
            conf = loader.load();
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
            conf = loader.load();
            return true;
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            loader.save(conf);
            return true;
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public YamlConfigurationLoader getLoader() {
        return loader;
    }

    @Override
    public ConfigurationNode getConf() {
        return conf;
    }

    @Override
    public boolean isStream() {
        return stream;
    }

    public InputStream getResourceAsStream(String fileName) {
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
