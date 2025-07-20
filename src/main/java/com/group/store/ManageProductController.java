package com.group.store;

import com.group.pojo.Category;
import com.group.utils.Configs;
import com.group.utils.MyAlert;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageProductController implements Initializable {
    @FXML TextField txtName;
    @FXML TextField txtPrice;
    @FXML TextField txtQuantity;
    @FXML ComboBox<Category> cbCategory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.cbCategory.setItems(FXCollections.observableList(Configs.f.listCategories()));
        } catch (Exception e) {
            MyAlert.getInstance().showMsg("LOAD_CATEGORY_FAILED");
        }
    }
}
