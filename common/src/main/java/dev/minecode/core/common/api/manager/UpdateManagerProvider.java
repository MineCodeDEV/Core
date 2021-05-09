package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.UpdateManager;
import dev.minecode.core.api.object.CorePlugin;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UpdateManagerProvider implements UpdateManager {

    private static final HashMap<CorePlugin, UpdateManagerProvider> updateManagerProviders = new HashMap<>();

    private final CorePlugin corePlugin;

    private URL url;
    private GsonConfigurationLoader loader;
    private ConfigurationNode conf;

    private boolean updateNotification,
            updatePreReleases;


    public UpdateManagerProvider(CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
        updateManagerProviders.put(corePlugin, this);
        makeInstances();
    }

    public static HashMap<CorePlugin, UpdateManagerProvider> getUpdateManagerProviders() {
        return updateManagerProviders;
    }

    private void makeInstances() {
        try {
            url = new URL("https://api.github.com/repos/MineCodeDEV/" + corePlugin.getName() + "/releases");
            loader = GsonConfigurationLoader.builder().url(url).build();
            conf = loader.load();
        } catch (ConfigurateException | MalformedURLException e) {
            e.printStackTrace();
        }

        updateNotification = CoreAPI.getInstance().getFileManager().getConfig().getConf().node("update", "notification").getBoolean();
        updatePreReleases = CoreAPI.getInstance().getFileManager().getConfig().getConf().node("update", "prereleases").getBoolean();
    }

    @Override
    public boolean updateAvailable() {
        String recommendRelease = getMatchingRelease();
        if (recommendRelease != null)
            return !getMatchingRelease().equals(corePlugin.getVersion());
        return false;
    }

    public String getMatchingRelease() {
        if (updatePreReleases)
            return getLatestRelease();
        return getLatestFullRelease();
    }

    @Override
    public String getReleaseURL(String version) {
        for (Map.Entry<Object, ? extends ConfigurationNode> node : conf.childrenMap().entrySet()) {
            if (node.getValue().node("tag_name").getString().equals(version)) {
                return conf.node("html_url").getString();
            }
        }
        return null;
    }

    @Override
    public String getLatestRelease() {
        for (Map.Entry<Object, ? extends ConfigurationNode> node : conf.childrenMap().entrySet()) {
            if (!node.getValue().node("draft").getBoolean()) {
                return node.getValue().node("tag_name").getString();
            }
        }
        return null;
    }

    @Override
    public boolean isUpdateNotification() {
        return updateNotification;
    }

    @Override
    public boolean isUpdatePreReleases() {
        return updatePreReleases;
    }

    @Override
    public String getLatestFullRelease() {
        for (Map.Entry<Object, ? extends ConfigurationNode> node : conf.childrenMap().entrySet()) {
            if (!node.getValue().node("prerelease").getBoolean()) {
                if (!node.getValue().node("draft").getBoolean()) {
                    return node.getValue().node("tag_name").getString();
                }
            }
        }
        return null;
    }

    @Override
    public String getLatestPreRelease() {
        for (Map.Entry<Object, ? extends ConfigurationNode> node : conf.childrenMap().entrySet()) {
            if (node.getValue().node("prerelease").getBoolean()) {
                if (!node.getValue().node("draft").getBoolean()) {
                    return node.getValue().node("tag_name").getString();
                }
            }
        }
        return null;
    }

}
