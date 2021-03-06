package dev.minecode.core.api.object;

import java.util.List;

public interface Language {

    CorePlugin getPlugin();

    String getIsocode();

    String getName();

    String getDisplayname();

    int getSlot();

    List<String> getLore();

    String getTexture();

    FileObject getFileObject();

}
