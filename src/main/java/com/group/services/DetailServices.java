package com.group.services;

import com.group.pojo.DetailInvoice;
import com.group.pojo.Product;
import com.group.repository.Cache;
import com.group.repository.UpdateRepository;

import java.sql.SQLException;
import java.util.List;

public class DetailServices {
    private UpdateRepository updateRepository = new UpdateRepository();
    public void importDetailInvoice (List<DetailInvoice> detailInvoiceList) throws SQLException {
        for (DetailInvoice detailInvoice : detailInvoiceList) {
            int changed = updateRepository.importDetailInvoices(detailInvoice);
            if (changed == 0) throw new SQLException();
        }
    }
}
