package dev.minecode.core.common.api.object;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.FileObject;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class FileObjectProvider implements FileObject {

    private static HashMap<String, FileObject> fileObjects = new HashMap<>();

    // directory
    private String directoryPath;

    // file
    private String fileName;
    private String filePath;
    private String fileStreamPath;
    private File file;

    // Configurate
    private YamlConfigurationLoader loader;
    private ConfigurationNode conf;

    // other
    private boolean stream;

    public FileObjectProvider(String fileName, String folder, String... subFolders) {
        this.fileName = fileName;
        StringBuilder subFolder = new StringBuilder();
        for (String temp : subFolders) {
            subFolder.append(temp + "/");
        }
        this.fileStreamPath = "/" + subFolder.toString() + fileName;
        this.directoryPath = "plugins/MineCode/" + folder + "/" + subFolder.toString() + "/";
        this.filePath = directoryPath + fileName;
        this.file = new File(directoryPath, fileName);
        createFile();
    }

    public FileObjectProvider(String fileName, String folder) {
        this.fileName = fileName;
        this.fileStreamPath = "/" + fileName;
        this.directoryPath = "plugins/MineCode/" + folder + "/";
        this.filePath = directoryPath + fileName;
        this.file = new File(directoryPath, fileName);
        createFile();
    }

    public FileObjectProvider(String fileName) {
        this.fileName = fileName;
        this.fileStreamPath = fileName;
        this.directoryPath = "plugins/MineCode/";
        this.filePath = directoryPath + fileName;
        this.file = new File(directoryPath, fileName);
        createFile();
    }

    @Override
    public FileObject createFile() {
        new File(directoryPath).mkdirs();
        if (!file.exists()) {
            InputStream inputStream = CoreAPI.getInstance().getResourceAsStream(fileStreamPath);
            if (inputStream != null) {
                try {
                    Files.copy(inputStream, file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stream = true;
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stream = false;
            }
        }
        loadConfig();
        return this;
    }

    @Override
    public void loadConfig() {
        this.loader = YamlConfigurationLoader.builder().file(file).build();
        try {
            conf = loader.load();
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            loader.save(conf);
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public String getDirectoryPath() {
        return directoryPath;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public String getFileStreamPath() {
        return fileStreamPath;
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

    public static HashMap<String, FileObject> getFileObjects() {
        return fileObjects;
    }
}
