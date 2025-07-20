package com.group.store;

import com.group.utils.JdbcConnector;
import com.group.utils.MyStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MyStage.getInstance(stage).show("hello-view.fxml");
    }

    @Override
    public void stop() throws Exception {
        JdbcConnector.getInstance().close();
    }

    public static void main(String[] args) {
        launch();
    }
}