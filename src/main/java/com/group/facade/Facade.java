package com.group.facade;

import com.group.pojo.Category;
import com.group.pojo.Product;
import com.group.services.CategoryServices;
import com.group.services.ProductServices;

import java.sql.SQLException;
import java.util.List;

public class Facade {
    CategoryServices categoryServices = new CategoryServices();
    ProductServices productServices = new ProductServices();
    public List<Category> listCategories() throws Exception {
        try {
            return categoryServices.listCategories();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Product> listProducts() throws Exception {
        return productServices.listProducts();
    }

    public void addProduct(Product product) throws SQLException {
        productServices.addProduct(product);
    }

    public void updateProduct(Product product) throws SQLException {
        productServices.updateProduct(product);
    }
}
