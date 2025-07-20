module com.group.store {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.group.store to javafx.fxml;
    exports com.group.store;
}