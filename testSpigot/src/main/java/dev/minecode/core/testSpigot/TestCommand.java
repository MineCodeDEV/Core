package dev.minecode.core.testSpigot;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.testSpigot.object.TestLanguageSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        CorePlayer corePlayer = CoreAPI.getInstance().getPlayerManager().getPlayer(commandSender.getName());
        Language corePlayerLanguage = corePlayer.getLanguage(Main.getCorePlugin());
        commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayerLanguage, TestLanguageSpigot.languageCommandTest)
                .corePlayer(corePlayer, "player")
                .language(corePlayerLanguage, "language")
                .chatcolorAll().getMessage());

        return false;
    }
}
