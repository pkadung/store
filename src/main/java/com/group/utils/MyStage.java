package com.group.utils;

import com.group.store.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class MyStage {
    private static MyStage instance;
    private Stage stage;
    private static Scene scene;

    private Stack<Parent> history = new Stack<Parent>();

    private MyStage(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Store");
    }

    public static MyStage getInstance(Stage stage) {
        if (instance == null) {
            instance = new MyStage(stage);
        }
        return instance;
    }

    public static MyStage getInstance() {
        return instance;
    }

    public void show(String fxml) throws IOException {
        Parent root = new FXMLLoader().load(App.class.getResource(fxml));
        if (scene != null) {
            history.push(scene.getRoot());
            scene.setRoot(root);
        }
        else scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void goBack() {
        if(!history.isEmpty()){
            scene.setRoot(history.pop());
            this.stage.setScene(scene);
            this.stage.show();
        }
    }

}
