package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.Language;
import net.md_5.bungee.api.chat.BaseComponent;

public interface ReplaceManager {

    ReplaceManager replaceAll(String toReplace, String replaceWith);

    ReplaceManager args(String command, String[] args, String replacement);

    ReplaceManager chatcolorColor();

    ReplaceManager chatcolorFormat();

    ReplaceManager chatcolorMagic();

    ReplaceManager chatcolorAll();

    ReplaceManager corePlayer(CorePlayer corePlayer, String replacement);

    ReplaceManager language(Language language, String replacement);

    String getMessage();

    BaseComponent[] getBaseMessage();
}
