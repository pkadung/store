package com.group.store;

import com.group.utils.MyStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class MenuController {
    public void handleCaculateMoney(ActionEvent event) throws IOException {
        MyStage.getInstance().show("caculateMoney.fxml");
    }

    public void handleHistoryInvoice(ActionEvent event) throws IOException {
        MyStage.getInstance().show("historyInvoice.fxml");
    }

    public void handleManageProduct(ActionEvent event) throws IOException {
        MyStage.getInstance().show("manageProduct.fxml");
    }
}