package dev.minecode.core.testSpigot.command;

import dev.minecode.core.api.CoreAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class TestSpigotCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length != 1) {
            commandSender.sendMessage("Arg-Length muss genau 1 sein");
            return false;
        }
        HashMap<String, String> message = new HashMap<>();
        message.put("key 1", "value 1");
        message.put("k1", "v1");
        CoreAPI.getInstance().getPluginMessageManager().sendPluginMessage(args[0], "minecode:pluginmessage", message);
        return false;
    }
}
