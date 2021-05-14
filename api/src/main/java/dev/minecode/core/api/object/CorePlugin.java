package dev.minecode.core.api.object;

public interface CorePlugin {

    Class getMainClass();

    String getName();

    String getVersion();

    boolean isLoadMessageFiles();

    void loadMessageFiles();

}
