package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.LanguageManager;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.core.common.api.object.LanguageProvider;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LanguageManagerProvider implements LanguageManager {

    public LanguageManagerProvider() {
        loadMessageFiles();
    }

    private void loadMessageFiles() {
        File messsageDirectory = new File("plugins/MineCode/" + CoreAPI.getInstance().getPluginName() + "/message/");
        if (messsageDirectory.mkdirs()) {
            for (Map.Entry<Object, ? extends ConfigurationNode> node : CoreAPI.getInstance().getFileManager().getConfig().getConf().node("language", "languages").childrenMap().entrySet()) {
                new LanguageProvider((String) node.getValue().key());
            }
        }
    }

    @Override
    public Object get(Language language, LanguageAbstract message) {
        Object object = null;
        if (language == null) language = CoreAPI.getInstance().getDefaultLanguage();

        try {
            object = language.getConfigurationNode().node(message.getPath()).get(Object.class);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public String getString(Language language, LanguageAbstract message) {
        return (String) get(language, message);
    }

    @Override
    public int getInt(Language language, LanguageAbstract message) {
        return (int) get(language, message);
    }

    @Override
    public boolean getBoolean(Language language, LanguageAbstract message) {
        return (boolean) get(language, message);
    }

    @Override
    public long getLong(Language language, LanguageAbstract message) {
        return (long) get(language, message);
    }

    @Override
    public List<Object> getList(Language language, LanguageAbstract message) {
        return (List<Object>) get(language, message);
    }

    @Override
    public List<String> getStringList(Language language, LanguageAbstract message) {
        List<String> stringList = new ArrayList<>();
        for (Object entry : getList(language, message)) stringList.add(entry.toString());
        return stringList;
    }
}
