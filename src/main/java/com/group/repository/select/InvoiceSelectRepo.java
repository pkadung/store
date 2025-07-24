package com.group.repository.select;

import com.group.pojo.Invoice;
import com.group.repository.SelectRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceSelectRepo extends SelectRepository<Invoice> {
    @Override
    public List<Invoice> getResult(ResultSet rs) throws SQLException {
        List<Invoice> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Invoice(rs.getInt("id"), rs.getString("name"), rs.getTime("date")));
        }
        return list;
    }

    @Override
    protected List<Invoice> getList() throws SQLException {
        return this.list("select * from invoice");
    }
}
