package dev.minecode.core.api.manager;

import java.sql.Connection;
import java.sql.Statement;

public interface DatabaseManager {

    boolean connect();

    boolean disconnect();

    Connection getConnection();

    Statement getStatement();

}
