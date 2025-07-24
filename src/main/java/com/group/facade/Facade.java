package com.group.facade;

import com.group.pojo.Category;
import com.group.pojo.DetailInvoice;
import com.group.pojo.Invoice;
import com.group.pojo.Product;
import com.group.services.CategoryServices;
import com.group.services.DetailServices;
import com.group.services.InvoiceService;
import com.group.services.ProductServices;
import com.group.utils.JdbcConnector;

import java.sql.SQLException;
import java.util.List;

public class Facade {
    private CategoryServices categoryServices = new CategoryServices();
    private ProductServices productServices = new ProductServices();
    private DetailServices detailServices = new DetailServices();
    private InvoiceService invoiceServices = new InvoiceService();
    public List<Category> listCategories() throws Exception {
        try {
            return categoryServices.listCategories();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Product> listProducts() throws Exception {
        try {
            return productServices.listProducts();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void addProduct(Product product) throws SQLException {
        productServices.addProduct(product);
    }

    public void updateProduct(Product product) throws SQLException {
        productServices.updateProduct(product);
    }

    public void exportInvoice(Invoice invoice, List<DetailInvoice> detailInvoices) throws SQLException {
        try {
            JdbcConnector.getInstance().connect().setAutoCommit(false);
            // lây id invoice từ thông tin khách hàng mới gửi
            int id = invoiceServices.getInvoiceIdAfterImport(invoice);
            // set thông tin invoid id
            detailInvoices.stream().forEach(item -> item.setInvoiceId(id));
            detailServices.importDetailInvoice(detailInvoices);
            productServices.updateAmount(detailInvoices);
            JdbcConnector.getInstance().connect().commit();
        } catch (SQLException e) {
            JdbcConnector.getInstance().connect().rollback();
            throw new SQLException(e.getMessage());
        }
    }
}
