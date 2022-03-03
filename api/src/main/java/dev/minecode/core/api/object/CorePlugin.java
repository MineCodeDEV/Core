package dev.minecode.core.api.object;

public interface CorePlugin {

    Class getMainClass();

    String getName();

    String getVersion();

    CorePluginVersion getPluginVersion();

    boolean isLoadMessageFiles();

    void loadMessageFiles();

}
