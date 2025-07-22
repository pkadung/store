package com.group.store;

import com.group.pojo.Category;
import com.group.pojo.Product;
import com.group.repository.Cache;
import com.group.utils.Configs;
import com.group.utils.MyAlert;
import com.group.utils.MyStage;
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
    @FXML Button btnAdd;
    @FXML TextField txtProductId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.cbCategory.setItems(FXCollections.observableList(Configs.f.listCategories()));
            this.loadColumn();
            this.tblProduct.setItems(FXCollections.observableList(Configs.f.listProducts()));
        } catch (Exception e) {
            MyAlert.getInstance().showMsg("LOAD_FAILED");
        }
        this.txtProductId.setDisable(true);
    }

    public void addProduct(ActionEvent event) throws SQLException {
        if(!this.validate(txtName.getText(),txtPrice.getText(),txtQuantity.getText(), cbCategory.getValue().getName())) {
            MyAlert.getInstance().showMsg("INVALID_INPUT_DATA");
            return;
        }
        try {
            Double price = Double.parseDouble(this.txtPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());
            int categoryId = cbCategory.getSelectionModel().getSelectedItem().getId();
            if (price <= 0.0 || categoryId <= 0 || quantity < 0) throw new Exception();
            if (btnAdd.getText().equals("Add")) {
                Configs.f.addProduct(new Product(txtName.getText(), categoryId, quantity, price));
            } else {
                Configs.f.updateProduct(new Product(Integer.parseInt(txtProductId.getText()), txtName.getText(), categoryId, quantity, price));
                this.btnAdd.setText("Add");
            }
            this.tblProduct.setItems(FXCollections.observableList(Configs.f.listProducts()));
            reset();
            MyAlert.getInstance().showMsg("PRODUCT_ADDED_SUCCESSFULLY");
        } catch (SQLException e) {
            MyAlert.getInstance().showMsg("PRODUCT_ADDED_FAILED");
        } catch (Exception e) {
            MyAlert.getInstance().showMsg("INVALID_INPUT_DATA");
        }
    }

    public void RepairInformation(Product product) {
        this.btnAdd.setText("Repair");
        this.txtName.setText(product.getName());
        for(Category c: this.cbCategory.getItems()) {
            if(product.getCategory_id() == c.getId()) {
                this.cbCategory.getSelectionModel().select(c);
            }
        }
        this.txtQuantity.setText(String.valueOf(product.getAmount()));
        this.txtPrice.setText(String.valueOf(product.getPrice()));
        this.txtProductId.setText(String.valueOf(product.getId()));
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
            btn.setOnAction(event -> {
                Product product = this.tblProduct.getItems().get(cell.getIndex());
                RepairInformation(product);
            });
            cell.setGraphic(btn);
            return cell;
        });

        List<TableColumn<Product,Object>> cols = new MyTableView.Builder<Product>()
                .addCol("Id", "id", 80)
                .addCol("Name", "name", 150)
                .addCol(colCategory).addCol("Quantity", "amount",100)
                .addCol("Price", "price", 100)
                .addCol(colRepair).build().getListCols();

        this.tblProduct.getColumns().addAll(cols);
    }

    public void goBack(ActionEvent event) {
        MyStage.getInstance().goBack();
    }
}
