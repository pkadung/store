package com.group.services;

import com.group.pojo.Category;
import com.group.repository.SelectRepository;
import com.group.repository.select.CategoryRepo;

import java.sql.SQLException;
import java.util.List;

public class CategoryServices {
    private CategoryRepo categoryRepo = new CategoryRepo();
    public List<Category> listCategories() throws SQLException {
        return categoryRepo.getList();
    }
}
