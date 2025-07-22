package com.group.repository;

import com.group.utils.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class RepositoryTemplate {
    protected Connection conn;

    public RepositoryTemplate() {
        try {
            this.conn = JdbcConnector.getInstance().connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement getStatement(String getSql, List<Object> params) throws SQLException {
        PreparedStatement stm = this.conn.prepareStatement(getSql);
        for (int i = 0; i < params.size(); i++) {
            stm.setObject(i + 1, params.get(i));
        }
        return stm;
    }
}
