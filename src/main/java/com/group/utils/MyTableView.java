package com.group.utils;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class MyTableView<T> {
    private List<TableColumn<T,Object>> cols;

    public MyTableView(Builder builder) {
        this.cols = builder.cols;
    }

    public List<TableColumn<T,Object>> getListCols() {
        return cols;
    }

    public static class Builder<T> {
        private List<TableColumn<T,Object>> cols = new ArrayList<>();

        public Builder<T> addCol(String colName, String valueFactory, int width) {
            TableColumn<T,Object> col = new TableColumn<>(colName);
            col.setCellValueFactory(new PropertyValueFactory(valueFactory));
            col.setPrefWidth(width);
            cols.add(col);
            return this;
        }

        public Builder<T> addCol(TableColumn<T,Object> col) {
            cols.add(col);
            return this;
        }

        public MyTableView<T> build() {
            return new MyTableView<T>(this);
        }
    }
}
