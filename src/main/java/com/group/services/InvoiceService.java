package com.group.services;

import com.group.pojo.Invoice;
import com.group.repository.Cache;
import com.group.repository.RepositoryTemplate;
import com.group.repository.UpdateRepository;
import com.group.repository.select.InvoiceSelectRepo;
import com.group.repository.select.ProductSelectRepo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InvoiceService {
    private UpdateRepository updateRepository = new UpdateRepository();
    private InvoiceSelectRepo invoiceSelectRepository = new InvoiceSelectRepo();

    public int getInvoiceIdAfterImport(Invoice invoice) throws SQLException {
        PreparedStatement stm = updateRepository.importInvoice(invoice);
        if (stm.executeUpdate() == 0) throw new SQLException();
        return updateRepository.getIdInvoice(stm);
    }

    public List<Invoice> getList() throws SQLException {
        return Cache.getData(invoiceSelectRepository, Invoice.TableName());
    }
}
