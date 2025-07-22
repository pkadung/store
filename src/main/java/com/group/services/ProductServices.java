package com.group.services;

import com.group.pojo.Product;
import com.group.repository.Cache;
import com.group.repository.UpdateRepository;
import com.group.repository.select.ProductSelectRepo;

import java.sql.SQLException;
import java.util.List;

public class ProductServices {
    private ProductSelectRepo productSelectRepo = new ProductSelectRepo();
    private UpdateRepository updateRepository = new UpdateRepository();
    public List<Product> listProducts() throws SQLException {
        return Cache.getData(productSelectRepo,Product.TableName());
    }
    public void addProduct(Product p) throws SQLException {
       int changed = updateRepository.addProduct(p);
       if (changed == 0) throw new SQLException();
       Cache.refresh(productSelectRepo,Product.TableName());
    }
    public void updateProduct(Product p) throws SQLException {
        int changed = updateRepository.updateProductByID(p);
        if (changed == 0) throw new SQLException();
        Cache.refresh(productSelectRepo,Product.TableName());
    }
}
