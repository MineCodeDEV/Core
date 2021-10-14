package dev.minecode.core.api.object;

public interface Language {

    CorePlugin getPlugin();

    String getIsocode();

    String getName();

    String getDisplayname();

    FileObject getFileObject();

}
