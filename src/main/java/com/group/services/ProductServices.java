package com.group.services;

import com.group.pojo.DetailInvoice;
import com.group.pojo.Product;
import com.group.repository.Cache;
import com.group.repository.UpdateRepository;
import com.group.repository.select.ProductSelectRepo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void updateAmount(List<DetailInvoice> detailInvoices) throws SQLException {
        Map<Integer, Integer> listUpdate = detailInvoices.stream().collect(Collectors.toMap(DetailInvoice::getProductId, DetailInvoice::getAmount));
        List<Product> products = Cache.getData(productSelectRepo,Product.TableName());
        for ( var item : listUpdate.entrySet()) {
            int productId = item.getKey();

            Product product = products.stream().filter(p->p.getId()==productId).findFirst().orElse(null);
            if (product == null) throw new SQLException();
            int newAmount = product.getAmount() - item.getValue();
            if (updateRepository.updateProductAmount(newAmount, productId) == 0 ) throw new SQLException("Failed to update product");
        }
        Cache.refresh(productSelectRepo,Product.TableName());
    }
}
