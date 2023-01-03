package dev.minecode.core.testSpigot.command;

import dev.minecode.core.api.CoreAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        HashMap<String, String> message = new HashMap<>();
        message.put("key1", "value1");
        message.put("key2", "value2");
        message.put("k3", "val3");
        CoreAPI.getInstance().getPluginMessageManager().sendPluginMessage(args[0], "MyTestchannel", message, true);

        return false;
    }
}
