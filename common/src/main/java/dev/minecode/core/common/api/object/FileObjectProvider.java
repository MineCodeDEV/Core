package dev.minecode.core.common.api.object;

import dev.minecode.core.api.object.FileObject;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

public class FileObjectProvider implements FileObject {

    // directories
    private static String minecodeDirectoryPath = "plugins/MineCode";
    private String pluginDirectoryPath;
    private String fileDirectoryPath;

    // file
    private String fileName;
    private String fileStreamPath;
    private File file;

    // Configurate
    private YamlConfigurationLoader loader;
    private ConfigurationNode conf;

    // other
    private boolean stream;

    public FileObjectProvider(String fileName, String pluginName, String... folders) {
        this.pluginDirectoryPath = minecodeDirectoryPath + "/" + pluginName;
        this.fileName = fileName;

        StringBuilder foldersStringBuilder = new StringBuilder();
        for (String temp : folders) {
            foldersStringBuilder.append(temp).append("/");
        }

        this.fileDirectoryPath = pluginDirectoryPath + "/" + foldersStringBuilder.toString();
        this.fileStreamPath = pluginName + "/" + foldersStringBuilder.toString() + fileName;
        this.file = new File(fileDirectoryPath, fileName);
        load();
    }

    public FileObjectProvider(String fileName, String pluginName) {
        this.pluginDirectoryPath = minecodeDirectoryPath + "/" + pluginName;
        this.fileName = fileName;

        this.fileDirectoryPath = pluginDirectoryPath;
        this.fileStreamPath = pluginName + "/" + fileName;
        this.file = new File(fileDirectoryPath, fileName);
        load();
    }

    public boolean load() {
        boolean success = true;
        if (!file.exists())
            success = createFile();

        if (success) return reload();
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
        this.loader = YamlConfigurationLoader.builder().file(file).build();
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
            URL url = this.getClass().getClassLoader().getResource(fileName);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException var4) {
            return null;
        }
    }
}
