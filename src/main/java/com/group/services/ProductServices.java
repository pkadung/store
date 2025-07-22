package com.group.services;

import com.group.pojo.Product;
import com.group.repository.Cache;
import com.group.repository.SelectRepository;
import com.group.repository.insert.ProductInsertRepo;
import com.group.repository.select.ProductRepo;

import java.sql.SQLException;
import java.util.List;

public class ProductServices {

    private ProductInsertRepo productInsertRepo = new ProductInsertRepo();
    private ProductRepo productSelectRepo = new ProductRepo();
    public List<Product> listProducts() throws Exception {
        return Cache.getData(productSelectRepo, Product.TableName());
    }

    public void addProduct(Product product) throws SQLException {
        if (productInsertRepo.addProduct(product) == 0) {
            throw new SQLException();
        }
        Cache.refresh(productSelectRepo, Product.TableName());
    }

    public void updateProduct(Product product) throws SQLException {
        if (productInsertRepo.updateProductById(product) == 0) {
            throw new SQLException();
        }
        Cache.refresh(productSelectRepo, Product.TableName());
    }
}
