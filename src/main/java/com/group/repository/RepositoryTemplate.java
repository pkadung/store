package com.group.repository;

import com.group.utils.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class RepositoryTemplate {
    protected Connection conn;

    public RepositoryTemplate() {
        try {
            this.conn = JdbcConnector.getInstance().connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
