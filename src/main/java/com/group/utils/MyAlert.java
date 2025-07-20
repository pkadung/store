package com.group.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MyAlert {
    private static MyAlert instance;
    private Alert alert;
    private MyAlert() {
        this.alert = new Alert(Alert.AlertType.INFORMATION);
        this.alert.setTitle("STORE");
    }
    public static MyAlert getInstance() {
        if (instance == null) {
            instance = new MyAlert();
        }
        return instance;
    }
    public void showMsg(String msg) {
        this.alert.setContentText(msg);
        this.alert.showAndWait();
    }

    public Optional<ButtonType> showMsg(String msg, Alert.AlertType type) {
        this.alert.setContentText(msg);
        this.alert.setAlertType(type);
        return this.alert.showAndWait();
    }
}


