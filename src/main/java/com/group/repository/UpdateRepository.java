package com.group.repository;

import com.group.pojo.DetailInvoice;
import com.group.pojo.Invoice;
import com.group.pojo.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public PreparedStatement importInvoice(Invoice invoice) throws SQLException {
        List<Object> params = new ArrayList<>();
        params.add(invoice.getName());
        return this.getStatement("insert into invoice(name) values(?)", params);
    }

    public int getIdInvoice(PreparedStatement stm) throws SQLException {
        ResultSet rs = stm.getGeneratedKeys();
        if (rs.next()) {return rs.getInt(1);}
        else return 0;
    }

    public int importDetailInvoices(DetailInvoice detailInvoice) throws SQLException {
        List<Object> params = new ArrayList<>();
        params.addAll(Arrays.asList(detailInvoice.getInvoiceId(), detailInvoice.getProductId(),  detailInvoice.getAmount(), detailInvoice.getPrice()));
        return this.getStatement("insert into detail_invoice(invoice_id, product_id, amount, price) values(?, ?, ?,?)", params).executeUpdate();
    }

    public int updateProductAmount(int amount, int id) throws SQLException {
        List<Object> params = new ArrayList<>();
        params.add(amount);
        params.add(id);
        return this.getStatement("update product set amount = ? where id = ?", params).executeUpdate();
    }
}
