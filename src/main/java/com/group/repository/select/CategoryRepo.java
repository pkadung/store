package com.group.repository.select;

import com.group.pojo.Category;
import com.group.repository.SelectRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepo extends SelectRepository<Category> {

    @Override
    public List<Category> getResult(ResultSet rs) throws SQLException {
        List<Category> listCategories = new ArrayList<>();
        while (rs.next()) {
            listCategories.add(new Category(rs.getInt("id"), rs.getString("name")));
        }
        return listCategories;
    }

    public List<Category> getList() throws SQLException {
        return this.list("select * from category");
    }
}

