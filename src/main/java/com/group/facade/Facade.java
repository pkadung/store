package com.group.facade;

import com.group.pojo.Category;
import com.group.services.CategoryServices;

import java.sql.SQLException;
import java.util.List;

public class Facade {
    CategoryServices categoryServices = new CategoryServices();
    public List<Category> listCategories() throws Exception {
        try {
            return categoryServices.listCategories();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }
}
