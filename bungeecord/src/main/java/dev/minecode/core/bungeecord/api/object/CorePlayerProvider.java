package dev.minecode.core.bungeecord.api.object;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.common.CoreCommon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CorePlayerProvider implements CorePlayer {

    private static ArrayList<CorePlayer> corePlayers = new ArrayList<>();

    private static UUID consoleUUID = new UUID(0, 0);
    private static int consoleID = 1;
    private static String consoleName = "CONSOLE";

    private static FileObject fileObject = CoreAPI.getInstance().getFileManager().getPlayers();
    private static ConfigurationNode conf = fileObject.getConf();

    private int id;
    private UUID uuid;
    private String name;
    private Language language;
    private boolean exists = false;
    private Statement statement;
    private ResultSet resultSet;

    public CorePlayerProvider(int id) {
        this.id = id;
        load();
    }

    public CorePlayerProvider(UUID uuid) {
        this.id = getID(uuid);
        this.uuid = uuid;
        load();
    }

    public CorePlayerProvider(String name) {
        this.id = getID(name);
        if (id != 0) {
            this.name = getName(id);
            exists = true;
            reload();
        } else {
            this.name = name;
            load();
        }
    }

    public void load() {
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                statement = CoreAPI.getInstance().getDatabaseManager().getStatement();
                resultSet = statement.executeQuery("SELECT * FROM minecode_players WHERE ID = " + id + "");
                exists = resultSet.next();
            } else
                exists = !conf.node(String.valueOf(id)).empty();

            if (!exists) {
                if (uuid == null && name == null) return;
                if (uuid == null && (uuid = getUuid(name)) == null) return;
                name = getName(uuid);
                if (uuid == consoleUUID && Objects.equals(name, consoleName)) id = consoleID;
                if (id == 0) id = generateNewID();
                if (id != 0 && uuid != null && name != null)
                    create(id, uuid, name, null);
                exists = true;
            } else
                reload();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean reload() {
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                resultSet = statement.executeQuery("SELECT * FROM minecode_players WHERE ID = " + id + "");
                if (resultSet.next()) {
                    uuid = UUID.fromString(resultSet.getString("UUID"));
                    name = resultSet.getString("NAME");
                    language = CoreAPI.getInstance().getLanguage(resultSet.getString("LANGUAGE"));
                    return true;
                } else {
                    load();
                    return exists;
                }
            } else {
                String tempUUID = conf.node(String.valueOf(id), "uuid").getString();
                if (tempUUID != null)
                    uuid = UUID.fromString(tempUUID);
                else uuid = null;
                name = conf.node(String.valueOf(id), "name").getString();
                language = CoreAPI.getInstance().getLanguage(conf.node(String.valueOf(id), "language").getString());
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                resultSet.updateString("UUID", uuid.toString());
                resultSet.updateString("NAME", name);
                resultSet.updateString("LANGUAGE", language.getIso_code());
                resultSet.updateRow();
                return true;
            } else {
                conf.node(String.valueOf(id), "uuid").set(uuid.toString());
                conf.node(String.valueOf(id), "name").set(name);
                conf.node(String.valueOf(id), "language").set(language.getIso_code());
                fileObject.save();
                return true;
            }
        } catch (SQLException | SerializationException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    private static void create(int id, UUID uuid, String name, Language language) {
        try {
            String iso_code = null;
            if (language != null)
                iso_code = language.getIso_code();

            if (CoreAPI.getInstance().isUsingSQL()) {
                if (iso_code != null)
                    iso_code = "'" + language + "'";
                CoreAPI.getInstance().getDatabaseManager().getStatement().executeUpdate("INSERT INTO minecode_players (ID, UUID, NAME, LANGUAGE) VALUES (" + id + ",'" + uuid.toString() + "', '" + name + "', " + iso_code + ")");
                return;
            }

            conf.node(String.valueOf(id), "uuid").set(uuid.toString());
            conf.node(String.valueOf(id), "name").set(name);
            conf.node(String.valueOf(id), "language").set(iso_code);
            fileObject.save();
        } catch (SQLException | SerializationException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public boolean setID(int id) {
        if (!isAvailableID(id)) return false;

        this.id = id;
        return true;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean setUuid(UUID uuid) {
        if (CoreCommon.getInstance().getUuidFetcher().getName(uuid) == null) return false;

        this.uuid = uuid;
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean setName(String name) {
        if (CoreCommon.getInstance().getUuidFetcher().getUUID(name) == null) return false;

        this.name = name;
        return true;
    }

    @Override
    public Language getLanguage() {
        return language;
    }

    @Override
    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean isExists() {
        return exists;
    }

    public static int getID(UUID uuid) {
        if (uuid.toString().equals(consoleUUID.toString())) return consoleID;
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT ID FROM minecode_players WHERE UUID = '" + uuid.toString() + "'");
                if (resultSet.next())
                    return resultSet.getInt("ID");
            } else
                for (Map.Entry<Object, ? extends ConfigurationNode> uuidNode : fileObject.getConf().childrenMap().entrySet())
                    if (!uuidNode.getValue().empty())
                        if (uuidNode.getValue().node("uuid").getString().equalsIgnoreCase(uuid.toString()))
                            return Integer.parseInt((String) uuidNode.getValue().key());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static int getID(String name) {
        if (name.equals(consoleName)) return consoleID;
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT ID FROM minecode_players UPPER(NAME) = UPPER('" + name + "')");
                if (resultSet.next())
                    return resultSet.getInt("ID");
            } else
                for (Map.Entry<Object, ? extends ConfigurationNode> uuidNode : fileObject.getConf().childrenMap().entrySet())
                    if (!uuidNode.getValue().empty())
                        if (uuidNode.getValue().node("name").getString().equalsIgnoreCase(name))
                            return Integer.parseInt((String) uuidNode.getValue().key());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static UUID getUuid(int id) {
        if (id == 0) return consoleUUID;
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT UUID FROM minecode_players WHERE ID = '" + id + "'");
                if (resultSet.next())
                    return UUID.fromString(resultSet.getString("UUID"));
            } else {
                String temp = conf.node(String.valueOf(id), "name").getString();
                if (temp != null) return UUID.fromString(temp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static UUID getUuid(String name) {
        if (name.equals(consoleName)) return consoleUUID;

        ProxiedPlayer proxiedPlayer;
        if ((proxiedPlayer = ProxyServer.getInstance().getPlayer(name)) != null)
            return proxiedPlayer.getUniqueId();

        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT UUID FROM minecode_players WHERE UPPER(NAME) = UPPER('" + name + "')");
                if (resultSet.next())
                    return UUID.fromString(resultSet.getString("UUID"));
            } else
                for (Map.Entry<Object, ? extends ConfigurationNode> uuidNode : fileObject.getConf().childrenMap().entrySet())
                    if (!uuidNode.getValue().empty())
                        if (uuidNode.getValue().node("uuid").getString().equalsIgnoreCase(name))
                            return UUID.fromString(uuidNode.getValue().node("uuid").getString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return CoreCommon.getInstance().getUuidFetcher().getUUID(name);
    }

    public static String getName(int id) {
        if (id == consoleID) return consoleName;
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT NAME FROM minecode_players WHERE ID = '" + id + "'");
                if (resultSet.next())
                    return resultSet.getString("NAME");
            } else {
                String temp = conf.node(String.valueOf(id), "name").getString();
                if (temp != null) return temp;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static String getName(UUID uuid) {
        if (uuid.toString().equals(consoleUUID.toString())) return consoleName;

        ProxiedPlayer proxiedPlayer;
        if ((proxiedPlayer = ProxyServer.getInstance().getPlayer(uuid)) != null)
            return proxiedPlayer.getName();

        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT NAME FROM minecode_players WHERE UUID = '" + uuid + "'");
                if (resultSet.next())
                    return resultSet.getString("NAME");
            } else {
                for (Map.Entry<Object, ? extends ConfigurationNode> uuidNode : fileObject.getConf().childrenMap().entrySet())
                    if (!uuidNode.getValue().empty())
                        if (uuidNode.getValue().node("uuid").getString().equalsIgnoreCase(uuid.toString()))
                            return uuidNode.getValue().node("name").getString();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return CoreCommon.getInstance().getUuidFetcher().getName(uuid);
    }

    public static boolean isAvailableID(int id) {
        return getUuid(id) != null && id > 0;
    }

    private static int generateNewID() {
        int id;
        do {
            id = new Random().nextInt(Integer.MAX_VALUE - 1);
        } while (isAvailableID(id));
        return id;
    }

    public static ArrayList<CorePlayer> getCorePlayers() {
        return corePlayers;
    }
}
