package com.group.store;

import com.group.pojo.Category;
import com.group.pojo.DetailInvoice;
import com.group.pojo.Invoice;
import com.group.pojo.Product;
import com.group.utils.Configs;
import com.group.utils.MyAlert;
import com.group.utils.MyStage;
import com.group.utils.MyTableView;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Delayed;
import java.util.stream.Collectors;

public class CaculateMoneyController implements Initializable {
    @FXML private TextField txtNameCustomer;
    @FXML private TextField txtAmount;
    @FXML private TextField txtPrice;
    @FXML private TextField txtTotalPrice;
    @FXML private TextField txtPriceInvoice;
    @FXML private TableView<DetailInvoice> tbDetailInvoice;
    @FXML private ComboBox<Product> cbProduct;
    private List<DetailInvoice> detailInvoices = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.txtPrice.setEditable(false);
        this.txtTotalPrice.setEditable(false);
        this.txtPriceInvoice.setEditable(false);

        try {
            this.cbProduct.setItems(FXCollections.observableList(Configs.f.listProducts()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.cbProduct.getSelectionModel().selectedItemProperty().addListener(event -> {
            Product product = this.cbProduct.getSelectionModel().getSelectedItem();
            this.txtPrice.setText(String.valueOf(product.getPrice()));
        });

        this.txtAmount.textProperty().addListener(event -> {
            int amount = 0;
            if(this.txtAmount.getText() != "" && !this.txtAmount.getText().isEmpty()) amount = Integer.parseInt(txtAmount.getText()) ;
            this.txtTotalPrice.setText(String.valueOf(Double.parseDouble(txtPrice.getText()) * amount));
        });

        this.loadColumn();
        this.cbProduct.getSelectionModel().selectFirst();
    }

    public void goBack(ActionEvent event) {
        MyStage.getInstance().goBack();
    }

    public void loadColumn() {
        TableColumn<DetailInvoice, Object> colProduct = new TableColumn<>("Product");
        colProduct.setCellValueFactory(cellData -> {
            int productId = cellData.getValue().getProductId();
            try {
                Map<Integer, String> mapProducts = Configs.f.listProducts().stream().collect(Collectors.toMap(Product::getId, Product::getName));
                String productName = mapProducts.get(productId);
                return new SimpleObjectProperty<Object>(productName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        colProduct.setMinWidth(150);

        TableColumn<DetailInvoice,Object> colRemove = new TableColumn();
        colRemove.setCellFactory(e -> {
            TableCell cell = new TableCell();

            Button btn = new Button("X");
            btn.setOnAction(event -> {
                DetailInvoice d = this.tbDetailInvoice.getItems().get(cell.getIndex());
                RemoveDetailInvoice(d);
            });
            cell.setGraphic(btn);
            return cell;
        });

        List<TableColumn<DetailInvoice,Object>> cols = new MyTableView.Builder<DetailInvoice>()
                .addCol(colProduct).addCol("Amount",  "amount", 100)
                .addCol("Price", "price", 100)
                .addCol(colRemove).build().getListCols();

        this.tbDetailInvoice.getColumns().addAll(cols);
    }

    public void addDetailInvoice(ActionEvent event) {
        // Kiểm tra giá trị
        try {
            this.validate(this.txtAmount.getText(), this.cbProduct.getSelectionModel().getSelectedItem().getName());
            int productId = this.cbProduct.getSelectionModel().getSelectedItem().getId();
            int amount = Integer.parseInt(this.txtAmount.getText()) + this.detailInvoices.stream()
                    .filter(item -> item.getProductId() == productId).mapToInt(DetailInvoice::getAmount).sum();
            double price = this.detailInvoices.stream().filter(item -> item.getProductId() == productId)
                    .mapToDouble(DetailInvoice::getPrice).sum();
            if (amount < 0 || amount > this.cbProduct.getSelectionModel().getSelectedItem().getAmount() || productId <= 0) throw new Exception();
            this.detailInvoices.removeIf(item -> item.getProductId() == productId);
            if (amount != 0) detailInvoices.add(new DetailInvoice(productId, amount, price + Double.parseDouble(txtTotalPrice.getText())));
            this.tbDetailInvoice.setItems(FXCollections.observableList(detailInvoices));
            this.updateTotalPrice();
        }
        catch (Exception e) {
            MyAlert.getInstance().showMsg("INVALID_INPUT");
        } finally {
            this.refresh();
        }
    }

    public void RemoveDetailInvoice(DetailInvoice d) {
        this.detailInvoices.removeIf(item -> item.getProductId() == d.getProductId());
        this.tbDetailInvoice.setItems(FXCollections.observableList(this.detailInvoices));
        this.updateTotalPrice();
    }

    public void updateTotalPrice() {
        this.txtPriceInvoice.setText(String.valueOf(this.detailInvoices.stream().mapToDouble(DetailInvoice::getPrice).sum()));
    }

    public void refresh() {
        this.txtAmount.clear();
    }

    public void validate(String... str) throws Exception {
        for (String s : str) {
            if (s.equals("")) throw new Exception();
        }
    }

    public void exportInvoice(ActionEvent event) {
        // Kiểm tra thử có khách hàng chưa
        try {
            this.validate(this.txtNameCustomer.getText());
            if (this.detailInvoices.size() == 0) throw new Exception();
            Configs.f.exportInvoice(new Invoice(this.txtNameCustomer.getText()), this.detailInvoices);
            this.txtNameCustomer.setText("");
            MyAlert.getInstance().showMsg("Invoice successfully exported");
            this.detailInvoices.clear();
            this.tbDetailInvoice.setItems(FXCollections.observableList(this.detailInvoices));
            this.txtPriceInvoice.setText("");
        } catch (SQLException e) {
            MyAlert.getInstance().showMsg(e.getMessage());
        }
        catch (Exception e) {
            MyAlert.getInstance().showMsg("INVALID_INPUT_DATA");
        }
    }
}
