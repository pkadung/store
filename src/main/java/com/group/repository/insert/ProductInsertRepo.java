package com.group.repository.insert;

import com.group.pojo.Product;
import com.group.repository.InsertRepository;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductInsertRepo extends InsertRepository {

    public int addProduct(Product product) throws SQLException {

        List<Object> params = new ArrayList<Object>();
        params.addAll(Arrays.asList(product.getName(),product.getCategory_id(),product.getAmount(), product.getPrice()));
        return this.getStatement("insert into product (name, category_id, amount, price) value (?,?,?,?)" , params).executeUpdate();
    }

    public int updateProductById(Product product) throws SQLException {

        List<Object> params = new ArrayList<>();
        params.addAll(Arrays.asList(product.getName(), product.getCategory_id(), product.getAmount(), product.getPrice(), product.getId()));
        return this.getStatement("update product set name = ?, category_id = ?, amount = ?, price = ? where id =?", params).executeUpdate();
    }

}
