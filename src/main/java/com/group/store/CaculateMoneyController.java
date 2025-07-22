package com.group.store;

import com.group.pojo.Category;
import com.group.pojo.DetailInvoice;
import com.group.pojo.Product;
import com.group.utils.Configs;
import com.group.utils.MyAlert;
import com.group.utils.MyStage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CaculateMoneyController implements Initializable {
    @FXML
    private TextField txtCustomerName;
    @FXML
    private ComboBox<Product> cbProductName;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtSumOneProduct;
    @FXML
    private TextField txtSumAllProduct;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private TableView<DetailInvoice> tblSelectedProducts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.txtPrice.setEditable(false);
        this.txtSumOneProduct.setEditable(false);
        this.txtSumAllProduct.setEditable(false);

        this.loadCollumn();

        try {
            this.cbProductName.setItems(FXCollections.observableList(Configs.f.listProducts()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.cbProductName.getSelectionModel().selectedItemProperty().addListener(e -> {
            Product product = this.cbProductName.getSelectionModel().getSelectedItem();
            this.txtPrice.setText(String.valueOf(product.getPrice()));

            this.txtAmount.textProperty().addListener(event -> {
                int amount = 0;
                if (!txtAmount.getText().isEmpty() && txtAmount.getText() != "")
                    amount = Integer.parseInt(txtAmount.getText());
                this.txtSumOneProduct.setText(String.valueOf(Double.parseDouble(txtPrice.getText()) * amount));
            });
        });

    }

    public void goBack(ActionEvent event) {
        MyStage.getInstance().goBack();
    }

    public void loadCollumn(){

        TableColumn<DetailInvoice, String> productIdCol = new TableColumn<>("Product");
        productIdCol.setCellValueFactory(cellData -> {
            int productId = cellData.getValue().getProductId();
            try {
                Map<Integer, String> mapCates = Configs.f.listProducts().stream().collect(Collectors.toMap(Product::getId,Product::getName));
                String productName = mapCates.get(productId);
                return new SimpleStringProperty(productName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        productIdCol.setPrefWidth(80);

        TableColumn quantityCol = new TableColumn("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory("amount"));
        quantityCol.setPrefWidth(80);

        TableColumn priceCol = new TableColumn("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory("price"));
        priceCol.setPrefWidth(80);

        TableColumn repairCol = new TableColumn("Repair");
        repairCol.setCellFactory(e -> {
            TableCell cell = new TableCell();

            Button btn = new Button("Repair");
           /* btn.setOnAction(event -> {
                Product product = tblProducts.getItems().get(cell.getIndex());
                RepairInformation(product);
            });*/
            cell.setGraphic(btn);
            return cell;
        });

        this.tblSelectedProducts.getColumns().addAll(productIdCol,quantityCol, priceCol, repairCol);
    }


    public void addProduct(ActionEvent event) {
        //Kiem tra cac gia tri nhap vao co Null hay ko ?

        try{
            this.validate(txtAmount.getText(), cbProductName.getSelectionModel().getSelectedItem().getName());
            double price = Double.parseDouble(txtPrice.getText());
            int amount = Integer.parseInt(txtAmount.getText());
            int productId = cbProductName.getSelectionModel().getSelectedItem().getId();

            if (amount < 0 || productId < 0 ) throw new Exception();

            this.refresh();

            MyAlert.getInstance().showMsg("Product added successfully");
        } catch (SQLException e) {
            MyAlert.getInstance().showMsg(e.getMessage());
        }
        catch(Exception e){
            MyAlert.getInstance().showMsg("Invalid input data!!");
        }
    }

    public void refresh(){
        txtAmount.clear();
        txtSumOneProduct.clear();
    }

    public void validate(String... str) throws Exception {
        for (String s : str){
            if (s.equals("")) throw new Exception();
        }
    }

}
