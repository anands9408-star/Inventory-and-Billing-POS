package com.college.pos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBUtil {
    private DBUtil() {}
    public static Connection getConnection() throws SQLException {
        String url = System.getenv().getOrDefault("POS_DB_URL", "jdbc:mysql://localhost:3306/pos_db");
        String user = System.getenv().getOrDefault("POS_DB_USER", "root");
        String password = System.getenv("POS_DB_PASSWORD");
        if (password == null) throw new SQLException("Set POS_DB_PASSWORD before starting the application.");
        return DriverManager.getConnection(url, user, password);
    }
}
