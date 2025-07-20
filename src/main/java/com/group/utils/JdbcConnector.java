package com.group.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnector {
    private static JdbcConnector instance;
    private Connection conn;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private JdbcConnector() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost/store", "root", "root");
    }

    public static JdbcConnector getInstance() throws SQLException {
        if (instance == null) {
            instance = new JdbcConnector();
        }
        return instance;
    }

    public Connection connect() throws SQLException {
        return this.conn;
    }

    public void close() throws SQLException {
        if (this.conn != null) this.conn.close();
    }
}
