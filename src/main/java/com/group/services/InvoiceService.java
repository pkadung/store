package com.group.services;

import com.group.pojo.Invoice;
import com.group.repository.UpdateRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InvoiceService {
    private UpdateRepository updateRepository = new UpdateRepository();
    public int getInvoiceIdAfterImport(Invoice invoice) throws SQLException {
        PreparedStatement stm = updateRepository.importInvoice(invoice);
        if (stm.executeUpdate() == 0) throw new SQLException();
        return updateRepository.getIdInvoice(stm);
    }
}
