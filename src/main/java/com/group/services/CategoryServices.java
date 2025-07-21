package com.group.services;

import com.group.pojo.Category;
import com.group.repository.Cache;
import com.group.repository.select.CategorySelectRepo;

import java.sql.SQLException;
import java.util.List;

public class CategoryServices {
    private CategorySelectRepo categorySelectRepo = new CategorySelectRepo();
    public List<Category> listCategories() throws SQLException {
        return Cache.getData(categorySelectRepo,Category.TableName());
    }
}
