package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.Language;

public interface ReplaceManager {

    ReplaceManager replaceAll(String toReplace, String replaceWith);

    ReplaceManager chatcolorColor();

    ReplaceManager chatcolorFormat();

    ReplaceManager chatcolorMagic();

    ReplaceManager chatcolorAll();

    ReplaceManager corePlayer(CorePlayer corePlayer, String replacement);

    ReplaceManager language(Language language, String replacement);

    String getMessage();
}
