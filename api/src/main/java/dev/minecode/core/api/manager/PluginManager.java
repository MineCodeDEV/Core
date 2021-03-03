package dev.minecode.core.api.manager;

public interface PluginManager {

    String getPluginName();

    String getPluginVersion();

    boolean isUsingSQL();

}
