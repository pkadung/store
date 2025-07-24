package com.group.store;

import com.group.pojo.Invoice;
import com.group.utils.Configs;
import com.group.utils.MyAlert;
import com.group.utils.MyTableView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryInvoiceController implements Initializable {
    @FXML TableView<Invoice> invoiceTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadColumn();
        try {
            this.invoiceTable.setItems(FXCollections.observableList(Configs.f.listInvoice()));
        } catch (Exception e) {
            MyAlert.getInstance().showMsg("CAN'T LOAD INVOICE");
        }
    }

    public void loadColumn()  {
        List<TableColumn<Invoice, Object>> columns = new MyTableView.Builder<Invoice>().addCol("Id", "id", 80)
                .addCol("Name", "name", 150).addCol("Date", "date", 100).build().getListCols();
        invoiceTable.getColumns().addAll(columns);
    }
}
