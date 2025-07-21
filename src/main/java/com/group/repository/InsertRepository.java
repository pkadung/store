package com.group.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class InsertRepository extends RepositoryTemplate {

    public PreparedStatement getStatement(String getSql, List<Object> params) throws SQLException {
        PreparedStatement stm = this.conn.prepareStatement(getSql);
        for (int i = 0; i < params.size(); i++) {
            stm.setObject(i + 1, params.get(i));
        }
        return stm;
    }

}
