package dev.minecode.core.testBungeeCord;

import dev.minecode.core.api.object.CorePlugin;
import dev.minecode.core.bungeecord.CoreBungeeCord;
import dev.minecode.core.testBungeeCord.command.TestBungeeCommand;
import dev.minecode.core.testBungeeCord.listener.MCPMReceiveListener;
import net.md_5.bungee.api.plugin.Plugin;

public class TestBungeeCord extends Plugin {

    private static TestBungeeCord instance;
    private CorePlugin testBungeeCordPlugin;

    public static TestBungeeCord getInstance() {
        return instance;
    }

    public void onEnable() {
        makeInstances();
        getProxy().getPluginManager().registerCommand(this, new TestBungeeCommand("testbungee", null, "tb"));
        new MCPMReceiveListener();
    }

    private void makeInstances() {
        instance = this;
        testBungeeCordPlugin = CoreBungeeCord.getInstance().registerPlugin(this, true);
    }
}
