package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.UpdateManager;
import dev.minecode.core.common.CoreCommon;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class UpdateManagerProvider implements UpdateManager {

    private URL url;
    private GsonConfigurationLoader loader;
    private ConfigurationNode conf;

    public UpdateManagerProvider() {
        try {
            url = new URL("https://api.github.com/repos/MineCodeDEV/" + CoreCommon.getInstance().getPluginName() + "/releases");
            loader = GsonConfigurationLoader.builder().url(url).build();
            conf = loader.load();
        } catch (ConfigurateException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateAvailable() {
        String recommendRelease = getMatchingRelease();
        if (recommendRelease != null)
            return !getMatchingRelease().equals(CoreAPI.getInstance().getPluginManager().getPluginVersion());
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
