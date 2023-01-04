package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.DatabaseManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;

import java.sql.*;

public class DatabaseManagerProvider implements DatabaseManager {

    private final ConfigurationNode root;
    private final boolean usingSQL;
    private Connection connection;
    private String host, database, username, password;
    private int port;
    private Statement statement;

    public DatabaseManagerProvider() {
        root = CoreAPI.getInstance().getFileManager().getDatabase().getRoot();
        usingSQL = root.node("enable").getBoolean();

        setData();
        if (usingSQL) {
            connect();
            checkTables();
        }
    }

    private void setData() {
        host = root.node("host").getString();
        port = root.node("port").getInt();
        database = root.node("database").getString();
        username = root.node("username").getString();
        password = root.node("password").getString();
    }

    @Override
    public boolean connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        try {
            if (connection != null || !connection.isClosed() || connection.isValid(2)) {
                connection.close();
                connection = null;
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void checkTables() {
        try {
            getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS minecode_players (UUID VARCHAR (37), NAME VARCHAR (16), LANGUAGE VARCHAR (5), PRIMARY KEY (UUID))");
            getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS minecode_pluginmessages (ID VARCHAR (37), TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, SENDERSERVER VARCHAR (100), TARGETSERVER VARCHAR (100), CHANNEL VARCHAR (50), MESSAGE TEXT, PRIMARY KEY (ID))");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public @Nullable Connection getConnection() {
        try {
            if (isUsingSQL())
                if (connection == null || connection.isClosed() || !connection.isValid(2))
                    connect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    @Override
    public @Nullable Statement getStatement() {
        try {
            statement = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return statement;
    }

    @Override
    public boolean isUsingSQL() {
        return usingSQL;
    }
}
