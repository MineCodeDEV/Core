package dev.minecode.core.spigot.util;

import dev.minecode.core.spigot.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;

public class BungeeCordPluginMessage {
    public static void connect(String targetPlayer, String targetServer) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("Connect");
            dataOutput.writeUTF(targetServer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(targetPlayer);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void connectOther(String executerPlayer, String targetPlayer, String targetServer) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("ConnectOther");
            dataOutput.writeUTF(targetPlayer);
            dataOutput.writeUTF(targetServer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(executerPlayer);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void ip(String targetPlayer) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("IP");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(targetPlayer);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void playerCount(String executerPlayer, String targetServer) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("PlayerCount");
            dataOutput.writeUTF(targetServer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(executerPlayer);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void playerList(String executerPlayer, String targetServer) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("PlayerList");
            dataOutput.writeUTF(targetServer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(executerPlayer);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void getServers(String executerPlayer) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("GetServers");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(executerPlayer);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void message(String executerPlayer, String targetPlayer, String message) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("Message");
            dataOutput.writeUTF(targetPlayer);
            dataOutput.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(executerPlayer);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void getServer(String executerPlayer) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("GetServer");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(executerPlayer);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void forward(String executerPlayer, String targetServer, String subChannel, ByteArrayOutputStream message) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);

        try {
            dataOutput.writeUTF("Forward");
            dataOutput.writeUTF(targetServer);
            dataOutput.writeUTF(subChannel);

            msgout.write(message.toByteArray());

            dataOutput.writeShort(message.toByteArray().length);
            dataOutput.write(message.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(executerPlayer);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void forwardToPlayer(String executerPlayer, String targetPlayer, String subChannel, ByteArrayOutputStream message) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);

        try {
            dataOutput.writeUTF("ForwardToPlayer");
            dataOutput.writeUTF(targetPlayer);
            dataOutput.writeUTF(subChannel);

            msgout.write(message.toByteArray());

            dataOutput.writeShort(msgbytes.toByteArray().length);
            dataOutput.write(msgbytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(executerPlayer);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void uuid(String targetName) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("UUID");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(targetName);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void uuidOther(String executerName, String targetName) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("UUIDOther");
            dataOutput.writeUTF(targetName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(executerName);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void serverIp(String executerName, String targetServer) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("ServerIP");
            dataOutput.writeUTF(targetServer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(executerName);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }

    public static void kickPlayer(String executerName, String targetPlayer, String reason) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            dataOutput.writeUTF("KickPlayer");
            dataOutput.writeUTF(targetPlayer);
            dataOutput.writeUTF(reason);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = Bukkit.getPlayer(executerName);
        player.sendPluginMessage(CoreSpigot.getInstance().getMainClass(), "BungeeCord", byteArrayOutput.toByteArray());
    }
}
