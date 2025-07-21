package com.group.store;

import com.group.pojo.Category;
import com.group.pojo.Product;
import com.group.repository.Cache;
import com.group.utils.Configs;
import com.group.utils.MyAlert;
import com.group.utils.MyTableView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ManageProductController implements Initializable {
    @FXML TextField txtName;
    @FXML TextField txtPrice;
    @FXML TextField txtQuantity;
    @FXML ComboBox<Category> cbCategory;
    @FXML TableView<Product> tblProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.cbCategory.setItems(FXCollections.observableList(Configs.f.listCategories()));
            this.loadColumn();
            this.tblProduct.setItems(FXCollections.observableList(Configs.f.listProducts()));
        } catch (Exception e) {
            MyAlert.getInstance().showMsg("LOAD_FAILED");
        }
    }

    public void addProduct(ActionEvent event) throws SQLException {
        if(!this.validate(txtName.getText(),txtPrice.getText(),txtQuantity.getText(), cbCategory.getValue().getName())) {
            MyAlert.getInstance().showMsg("INVALID_INPUT_DATA");
            return;
        }
        try {
            Double price = Double.parseDouble(this.txtPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());
            int categoryId = cbCategory.getValue().getId();
            if (price <= 0.0 || categoryId <= 0 || quantity < 0) throw new Exception();
            Configs.f.addProduct(new Product(txtName.getText(), categoryId, quantity, price));
            this.tblProduct.setItems(FXCollections.observableList(Configs.f.listProducts()));
            reset();
            MyAlert.getInstance().showMsg("PRODUCT_ADDED_SUCCESSFULLY");
        } catch (SQLException e) {
            MyAlert.getInstance().showMsg("PRODUCT_ADDED_FAILED");
        } catch (Exception e) {
            MyAlert.getInstance().showMsg("INVALID_INPUT_DATA");
        }
    }

    public void reset() {
        txtName.clear();
        txtPrice.clear();
        txtQuantity.clear();
        cbCategory.setValue(null);
    }

    public boolean validate(String... str) {
        for (String s : str) {
            if (s.equals("")) {return  false;}
        }
        return true;
    }

    public void loadColumn() {

        TableColumn<Product, Object> colCategory = new TableColumn<>("Category");
        colCategory.setCellValueFactory(cellData -> {
            int categoryId = cellData.getValue().getCategory_id();
            try {
                Map<Integer, String> mapCates = Configs.f.listCategories().stream().collect(Collectors.toMap(Category::getId, Category::getName));
                String categoryName = mapCates.get(categoryId);
                return new SimpleObjectProperty<Object>(categoryName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        colCategory.setMinWidth(150);

        TableColumn<Product,Object> colRepair = new TableColumn();
        colRepair.setCellFactory(e -> {
            TableCell cell = new TableCell();

            Button btn = new Button("Repair");
            cell.setGraphic(btn);
            return cell;
        });

        TableColumn<Product,Object> colRemove = new TableColumn();
        colRemove.setCellFactory(e -> {
            TableCell cell = new TableCell();

            Button btn = new Button("X");
            cell.setGraphic(btn);
            return cell;
        });

        List<TableColumn<Product,Object>> cols = new MyTableView.Builder<Product>()
                .addCol("Id", "id", 80)
                .addCol("Name", "name", 150)
                .addCol(colCategory).addCol("Quantity", "amount",100)
                .addCol("Price", "price", 100)
                .addCol(colRepair).addCol(colRemove).build().getListCols();

        this.tblProduct.getColumns().addAll(cols);
    }
}
