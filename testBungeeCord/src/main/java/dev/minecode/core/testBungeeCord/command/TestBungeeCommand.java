package dev.minecode.core.testBungeeCord.command;

import dev.minecode.core.api.CoreAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;

public class TestBungeeCommand extends Command {
    public TestBungeeCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args.length != 1) {
            commandSender.sendMessage("Arg-Length muss genau 1 sein");
            return;
        }
        HashMap<String, String> message = new HashMap<>();
        message.put("key 1", "value 1");
        message.put("k1", "v1");
        CoreAPI.getInstance().getPluginMessageManager().sendPluginMessage(args[0], "EinSehrCoolerChannel", message, true);
    }
}
