package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.UpdateManager;
import dev.minecode.core.api.object.CorePlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class UpdateManagerProvider implements UpdateManager {

    private final CorePlugin corePlugin;

    private ConfigurationNode conf;
    private boolean updateNotification, updatePreReleases;


    public UpdateManagerProvider(@NotNull CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
        makeInstances();
    }

    private void makeInstances() {
        try {
            URL url = new URL("https://api.github.com/repos/MineCodeDEV/" + corePlugin.getName() + "/releases");
            GsonConfigurationLoader loader = GsonConfigurationLoader.builder().url(url).build();
            conf = loader.load();
        } catch (ConfigurateException | MalformedURLException e) {
            e.printStackTrace();
        }

        updateNotification = CoreAPI.getInstance().getFileManager().getConfig().getRoot().node("update", "notification").getBoolean();
        updatePreReleases = CoreAPI.getInstance().getFileManager().getConfig().getRoot().node("update", "prereleases").getBoolean();
    }

    @Override
    public boolean checkUpdateAvailable() {
        String recommendRelease = getMatchingRelease();
        if (recommendRelease != null)
            return !getMatchingRelease().equals(corePlugin.getVersion());
        return false;
    }

    public @Nullable String getMatchingRelease() {
        if (updatePreReleases)
            return getLatestRelease();
        return getLatestFullRelease();
    }

    @Override
    public @Nullable String getReleaseURL(@NotNull String version) {
        for (Map.Entry<Object, ? extends ConfigurationNode> node : conf.childrenMap().entrySet())
            if (node.getValue().node("tag_name").getString().equals(version))
                return node.getValue().node("html_url").getString();
        return null;
    }

    @Override
    public @Nullable String getLatestRelease() {
        for (Map.Entry<Object, ? extends ConfigurationNode> node : conf.childrenMap().entrySet())
            if (!node.getValue().node("draft").getBoolean()) return node.getValue().node("tag_name").getString();
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
    public @Nullable String getLatestFullRelease() {
        for (Map.Entry<Object, ? extends ConfigurationNode> node : conf.childrenMap().entrySet())
            if (!node.getValue().node("prerelease").getBoolean())
                if (!node.getValue().node("draft").getBoolean()) return node.getValue().node("tag_name").getString();
        return null;
    }

    @Override
    public @Nullable String getLatestPreRelease() {
        for (Map.Entry<Object, ? extends ConfigurationNode> node : conf.childrenMap().entrySet())
            if (node.getValue().node("prerelease").getBoolean())
                if (!node.getValue().node("draft").getBoolean()) return node.getValue().node("tag_name").getString();
        return null;
    }

}
