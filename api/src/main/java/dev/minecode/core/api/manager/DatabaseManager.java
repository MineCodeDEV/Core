package dev.minecode.core.api.manager;

import java.sql.Connection;
import java.sql.Statement;

public interface DatabaseManager {

    void connect();

    void disconnect();

    void checkTables();

    Connection getConnection();

    Statement getStatement();

}
