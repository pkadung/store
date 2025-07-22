package com.group.repository;

import com.group.pojo.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateRepository extends RepositoryTemplate {
    public int addProduct(Product product) throws SQLException {
        List<Object> params = new ArrayList<>();
        params.add(product.getName());
        params.add(product.getCategory_id());
        params.add(product.getAmount());
        params.add(product.getPrice());
        return this.getStatement("insert into product(name, category_id, amount, price) value(?,?,?,?)", params).executeUpdate();
    }

    public int updateProductByID(Product product) throws SQLException {
        List<Object> params = new ArrayList<>();
        params.add(product.getName());
        params.add(product.getCategory_id());
        params.add(product.getAmount());
        params.add(product.getPrice());
        params.add(product.getId());
        return this.getStatement("update product set name = ?, category_id = ?, amount = ?, price = ? where id = ?", params).executeUpdate();
    }
}
