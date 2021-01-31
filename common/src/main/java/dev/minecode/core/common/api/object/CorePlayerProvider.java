package dev.minecode.core.common.api.object;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.common.CoreCommon;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class CorePlayerProvider implements CorePlayer {

    private static HashMap<Integer, CorePlayerProvider> idCache = new HashMap<>();
    private static HashMap<UUID, CorePlayerProvider> nameCache = new HashMap<>();
    private static HashMap<String, CorePlayerProvider> uuidCache = new HashMap<>();

    private static UUID consoleUUID = new UUID(0, 0);
    private static int consoleID = 1;
    private static String consoleName = "CONSOLE";

    private static FileObject fileObject = CoreAPI.getInstance().getFileManager().getPlayers();
    private static ConfigurationNode conf = fileObject.getConf();

    private int id;
    private UUID uuid;
    private String name;
    private Language language;
    private boolean exists;
    private Statement statement;
    private ResultSet resultSet;

    public CorePlayerProvider(int id) {
        this.id = id;
        load();
    }

    public CorePlayerProvider(UUID uuid) {
        this.id = CorePlayerProvider.getID(uuid);
        this.uuid = uuid;
        load();
    }

    public CorePlayerProvider(String name) {
        this.id = CorePlayerProvider.getID(name);
        this.name = name;
        load();
    }

    private static void create(int id, UUID uuid, String name, String language) {
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                if (language != null)
                    language = "'" + language + "'";
                CoreAPI.getInstance().getDatabaseManager().getStatement().executeUpdate("INSERT INTO minecode_players (ID, UUID, NAME, LANGUAGE) VALUES ('" + id + "','" + uuid.toString() + "', '" + name + "', " + language + ")");
                return;
            }

            conf.node(id, "uuid").set(uuid.toString());
            conf.node(id, "name").set(name);
            conf.node(id, "language").set(language);
            fileObject.save();
        } catch (SQLException | SerializationException throwables) {
            throwables.printStackTrace();
        }
    }

    public void load() {
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                statement = CoreAPI.getInstance().getDatabaseManager().getStatement();
                resultSet = statement.executeQuery("SELECT * FROM minecode_players WHERE ID = '" + id + "'");
                exists = resultSet.next();
            } else
                exists = !conf.node(id).empty();

            if (!exists) {
                if (name == null) name = getName(id);
                if (uuid == null) uuid = getUuid(id);
                create(generateNewID(), uuid, name, null);
                load();
            }

            reload();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update() {
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                resultSet.updateString("NAME", name);
                resultSet.updateString("LANGUAGE", language.getIso_code());
                resultSet.updateRow();
            } else {
                conf.node(id, "name").set(name);
                conf.node(id, "language").set(language.getIso_code());
                fileObject.save();
            }
        } catch (SQLException | SerializationException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void reload() {
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                resultSet = statement.executeQuery("SELECT * FROM minecode_players WHERE ID = '" + id + "'");
                uuid = UUID.fromString(resultSet.getString("UUID"));
                language = CoreAPI.getInstance().getLanguage(resultSet.getString("LANGUAGE"));
                name = resultSet.getString("NAME");
            } else {
                uuid = UUID.fromString(conf.node(id, "uuid").getString());
                name = conf.node(id, "name").getString();
                language = CoreAPI.getInstance().getLanguage(conf.node(id, "language").getString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Language getLanguage() {
        return language;
    }

    @Override
    public void setLanguage(Language language) {
        this.language = language;
    }

    public static int getID(UUID uuid) {
        if (uuid.toString().equals(consoleUUID.toString())) return consoleID;
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT ID FROM minecode_players WHERE UUID = '" + uuid.toString() + "'");
                if (resultSet.next())
                    return resultSet.getInt("ID");
            } else
                for (Map.Entry<Object, ? extends ConfigurationNode> uuidNode : fileObject.getConf().childrenMap().entrySet()) {
                    if (!uuidNode.getValue().empty())
                        if (uuidNode.getValue().node("uuid").getString().equalsIgnoreCase(uuid.toString()))
                            return (int) uuidNode.getValue().key();
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String name = CoreCommon.getInstance().getUuidFetcher().getName(uuid);
        if (name != null)
            CorePlayerProvider.create(generateNewID(), uuid, name, null);
        return 0;
    }

    public static int getID(String name) {
        if (name.equals(consoleName)) return consoleID;
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT ID FROM minecode_players WHERE NAME = '" + name + "'");
                if (resultSet.next())
                    return resultSet.getInt("ID");
            } else
                for (Map.Entry<Object, ? extends ConfigurationNode> uuidNode : fileObject.getConf().childrenMap().entrySet()) {
                    if (!uuidNode.getValue().empty())
                        if (uuidNode.getValue().node("name").getString().equalsIgnoreCase(name))
                            return (int) uuidNode.getValue().key();
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        UUID uuid = CoreCommon.getInstance().getUuidFetcher().getUUID(name);
        if (uuid != null)
            CorePlayerProvider.create(generateNewID(), uuid, name, null);
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
                String temp = conf.node(id, "name").getString();
                if (temp != null) return UUID.fromString(temp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static UUID getUuid(String name) {
        if (name.equals(consoleName)) return consoleUUID;
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT UUID FROM minecode_players WHERE NAME = '" + name + "'");
                if (resultSet.next())
                    return UUID.fromString(resultSet.getString("UUID"));
            } else
                for (Map.Entry<Object, ? extends ConfigurationNode> uuidNode : fileObject.getConf().childrenMap().entrySet()) {
                    if (!uuidNode.getValue().empty())
                        if (uuidNode.getValue().node("uuid").getString().equalsIgnoreCase(name))
                            return UUID.fromString(uuidNode.getValue().node("uuid").getString());
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        UUID uuid = CoreCommon.getInstance().getUuidFetcher().getUUID(name);
        if (uuid != null) CorePlayerProvider.create(generateNewID(), uuid, name, null);
        return uuid;
    }

    public static String getName(int id) {
        if (id == consoleID) return consoleName;
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT NAME FROM minecode_players WHERE ID = '" + id + "'");
                if (resultSet.next())
                    return resultSet.getString("NAME");
            } else {
                String temp = conf.node(id, "name").getString();
                if (temp != null) return temp;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static String getName(UUID uuid) {
        if (uuid.toString().equals(consoleUUID.toString())) return consoleName;
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                ResultSet resultSet = CoreAPI.getInstance().getDatabaseManager().getStatement().executeQuery("SELECT NAME FROM minecode_players WHERE UUID = '" + uuid + "'");
                if (resultSet.next())
                    return resultSet.getString("NAME");
            } else {
                for (Map.Entry<Object, ? extends ConfigurationNode> uuidNode : fileObject.getConf().childrenMap().entrySet()) {
                    if (!uuidNode.getValue().empty())
                        if (uuidNode.getValue().node("uuid").getString().equalsIgnoreCase(uuid.toString()))
                            return uuidNode.getValue().node("name").getString();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String name = CoreCommon.getInstance().getUuidFetcher().getName(uuid);
        if (name != null) CorePlayerProvider.create(generateNewID(), uuid, name, null);
        return name;
    }

    public static boolean isExists(int id) {
        if (getUuid(id) != null) return true;
        return false;
    }

    public static boolean isExists(UUID uuid) {
        if (getID(uuid) != 0) return true;
        return false;
    }

    public static boolean isExists(String name) {
        if (getID(name) != 0) return true;
        return false;
    }

    public static boolean isAvailableID(int id) {
        return getUuid(id) != null && id > 0;
    }

    private static int generateNewID() {
        int id = 0;
        do {
            id = new Random().nextInt(Integer.MAX_VALUE);
        } while (isAvailableID(id));
        return id;
    }

    public static HashMap<Integer, CorePlayerProvider> getIdCache() {
        return idCache;
    }

    public static HashMap<UUID, CorePlayerProvider> getNameCache() {
        return nameCache;
    }

    public static HashMap<String, CorePlayerProvider> getUuidCache() {
        return uuidCache;
    }
}