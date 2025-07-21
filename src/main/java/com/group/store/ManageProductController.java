package com.group.store;

import com.group.pojo.Category;
import com.group.pojo.Product;
import com.group.utils.Configs;
import com.group.utils.MyAlert;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ManageProductController implements Initializable {
    @FXML TextField txtName;
    @FXML TextField txtPrice;
    @FXML TextField txtQuantity;
    @FXML ComboBox<Category> cbCategory;
    @FXML TableView<Product> tblProducts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.cbCategory.setItems(FXCollections.observableList(Configs.f.listCategories()));
            this.loadCollumn();
            this.tblProducts.setItems(FXCollections.observableList(Configs.f.listProducts()));
        } catch (Exception e) {
            MyAlert.getInstance().showMsg(e.getMessage());
        }
    }

    public void loadCollumn(){
        TableColumn idCol = new TableColumn("ID");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        idCol.setPrefWidth(80);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.setPrefWidth(150);

        TableColumn<Product, String> categoryIdCol = new TableColumn<>("Category");
        categoryIdCol.setCellValueFactory(cellData -> {
            int categoryId = cellData.getValue().getCategory_id();
            try {
                Map<Integer, String> mapCates = Configs.f.listCategories().stream().collect(Collectors.toMap(Category::getId,Category::getName));
                String categoryName = mapCates.get(categoryId);
                return new SimpleStringProperty(categoryName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        categoryIdCol.setPrefWidth(80);

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
            cell.setGraphic(btn);
            return cell;
        });

        TableColumn delCol = new TableColumn("Delete");
        delCol.setCellFactory( e -> {
            TableCell cell = new TableCell();
            Button btn = new Button("Delete");

            cell.setGraphic(btn);
            return cell;
        });

        this.tblProducts.getColumns().addAll(idCol, nameCol, categoryIdCol, quantityCol, priceCol, repairCol, delCol);
    }

    public void addProduct(ActionEvent event) {
        //Kiem tra cac gia tri nhap vao co Null hay ko ?
        //Convert cac gia tri ve dung kieu, sai se xuat hien thong bao
        //Di vao DB va them Product(...)

        try{
            this.validate(txtName.getText(), txtPrice.getText(), txtQuantity.getText(), txtQuantity.getText(),cbCategory.getValue().getName());
            double price = Double.parseDouble(txtPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());
            int categoryId = cbCategory.getValue().getId();

            if (price <=0 || quantity < 0 || categoryId < 0 ) throw new Exception();

            Configs.f.addProduct( new Product(txtName.getText(), categoryId, quantity, price));
            this.tblProducts.setItems(FXCollections.observableList(Configs.f.listProducts()));
            this.refresh();

            MyAlert.getInstance().showMsg("Product added successfully");
        } catch (SQLException e) {
            MyAlert.getInstance().showMsg("Failed to add product");
        }
        catch(Exception e){
            MyAlert.getInstance().showMsg("Invalid input data!!");
        }
    }

    public void validate(String... str) throws Exception {
        for (String s : str){
            if (s.equals("")) throw new Exception();
        }
    }

    public void refresh(){
        txtName.clear();
        txtPrice.clear();
        txtQuantity.clear();
        cbCategory.setValue(null);
    }
}
