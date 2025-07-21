package com.group.repository.insert;

import com.group.pojo.Product;
import com.group.repository.InsertRepository;
import com.group.repository.RepositoryTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductInsertRepo extends InsertRepository {
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
