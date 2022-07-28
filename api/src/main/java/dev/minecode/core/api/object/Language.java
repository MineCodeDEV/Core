package dev.minecode.core.api.object;

import org.jetbrains.annotations.NotNull;

public interface Language {

    @NotNull CorePlugin getPlugin();

    @NotNull String getIsocode();

    @NotNull String getName();

    @NotNull String getDisplayname();

    @NotNull FileObject getFileObject();
}
