package com.group.store;

import com.group.pojo.Category;
import com.group.pojo.DetailInvoice;
import com.group.pojo.Product;
import com.group.utils.Configs;
import com.group.utils.MyAlert;
import com.group.utils.MyStage;
import com.group.utils.MyTableView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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

        TableColumn<DetailInvoice,Object> colRepair = new TableColumn();
        colRepair.setCellFactory(e -> {
            TableCell cell = new TableCell();

            Button btn = new Button("Repair");
            btn.setOnAction(event -> {

            });
            cell.setGraphic(btn);
            return cell;
        });

        TableColumn<DetailInvoice,Object> colRemove = new TableColumn();
        colRemove.setCellFactory(e -> {
            TableCell cell = new TableCell();

            Button btn = new Button("X");
            btn.setOnAction(event -> {

            });
            cell.setGraphic(btn);
            return cell;
        });

        List<TableColumn<DetailInvoice,Object>> cols = new MyTableView.Builder<DetailInvoice>()
                .addCol(colProduct).addCol("Amount",  "amount", 100)
                .addCol("Price", "price", 100)
                .addCol(colRepair).addCol(colRemove).build().getListCols();

        this.tbDetailInvoice.getColumns().addAll(cols);
    }

    public void addDetailInvoice(ActionEvent event) {
        // Kiểm tra giá trị
        try {
            this.validate(this.txtAmount.getText(), this.cbProduct.getSelectionModel().getSelectedItem().getName());
            int amount = Integer.parseInt(this.txtAmount.getText());
            int productId = this.cbProduct.getSelectionModel().getSelectedItem().getId();
            if (amount <= 0 || amount >  this.cbProduct.getSelectionModel().getSelectedItem().getAmount() || productId <= 0) throw new Exception();
            detailInvoices.add(new DetailInvoice(productId, amount, Double.parseDouble(txtPrice.getText())));
            this.tbDetailInvoice.setItems(FXCollections.observableList(detailInvoices));
        } catch (Exception e) {
            MyAlert.getInstance().showMsg("INVALID_INPUT_DATA");
        }
    }

    public void validate(String... str) throws Exception {
        for (String s : str) {
            if (s.equals("")) throw new Exception();
        }
    }
}
