package dev.minecode.core.api.object;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface CorePlugin {

    @NotNull Class getMainClass();

    @NotNull String getName();

    @NotNull String getVersion();

    @NotNull PluginPlattform getPlattform();

    @NotNull File getDataFolder();

    boolean isLoadMessageFiles();

    void loadMessageFiles();

}
