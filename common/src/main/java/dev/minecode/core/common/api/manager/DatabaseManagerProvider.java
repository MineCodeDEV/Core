package dev.minecode.core.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.DatabaseManager;
import org.spongepowered.configurate.ConfigurationNode;

import java.sql.*;

public class DatabaseManagerProvider implements DatabaseManager {

    private Connection connection;
    private String host, database, username, password;
    private int port;
    private Statement statement;

    public DatabaseManagerProvider() {
        setData();
        if (CoreAPI.getInstance().isUsingSQL()) {
            connect();
            checkTables();
        }
    }

    private void setData() {
        ConfigurationNode conf = CoreAPI.getInstance().getFileManager().getConfig().getConf();
        host = conf.node("database", "host").getString();
        port = conf.node("database", "port").getInt();
        database = conf.node("database", "database").getString();
        username = conf.node("database", "username").getString();
        password = conf.node("database", "password").getString();
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
            getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS minecode_players (ID INT, UUID VARCHAR (37), NAME VARCHAR (16), LANGUAGE VARCHAR (5), PRIMARY KEY (ID))");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        try {
            if (CoreAPI.getInstance().isUsingSQL()) {
                if (connection == null || connection.isClosed() || !connection.isValid(2))
                    connect();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    @Override
    public Statement getStatement() {
        getConnection();
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return statement;
    }
}
