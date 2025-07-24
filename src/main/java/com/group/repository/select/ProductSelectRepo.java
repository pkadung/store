package com.group.repository.select;

import com.group.pojo.Product;
import com.group.repository.SelectRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductSelectRepo extends SelectRepository<Product> {

    @Override
    public List<Product> getResult(ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<>();
        while(rs.next()){
            products.add(new Product(rs.getInt("id"), rs.getString("name"),
                    rs.getInt("category_id"), rs.getInt("amount"), rs.getDouble("price")));
        }
        return products;
    }

    @Override
    protected List<Product> getList() throws SQLException {
        return this.list("select * from product");
    }

}
