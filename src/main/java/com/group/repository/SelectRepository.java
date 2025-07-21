package com.group.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class SelectRepository<T> extends RepositoryTemplate{
    public abstract List<T> getResult(ResultSet rs) throws SQLException;
    public PreparedStatement getStatement(String getSql, List<Objects> params) throws SQLException  {
        PreparedStatement stm = this.conn.prepareStatement(getSql);
        for (int i = 0; i < params.size(); i++) {
            stm.setObject(i + 1, params.get(i));
        }
        return stm;
    }

    public abstract List<T> getList() throws SQLException;

    protected List<T> list(String getSql, Objects... params) throws SQLException {
        return getResult(getStatement(getSql, List.of(params)).executeQuery());
    }
}
