package dev.minecode.core.api.manager;

import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.Language;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

public interface ReplaceManager {

    @NotNull ReplaceManager replaceAll(@NotNull String toReplace, @NotNull String replaceWith);

    @NotNull ReplaceManager args(@NotNull String command, @NotNull String[] args, @NotNull String replacement);

    @NotNull ReplaceManager chatcolorColor();

    @NotNull ReplaceManager chatcolorFormat();

    @NotNull ReplaceManager chatcolorMagic();

    @NotNull ReplaceManager chatcolorAll();

    @NotNull ReplaceManager corePlayer(@NotNull CorePlayer corePlayer, @NotNull String replacement);

    @NotNull ReplaceManager language(@NotNull Language language, @NotNull String replacement);

    @NotNull String getMessage();

    @NotNull BaseComponent[] getBaseMessage();
}
