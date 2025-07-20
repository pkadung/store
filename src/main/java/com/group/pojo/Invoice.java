package com.group.pojo;

import java.sql.Time;
import java.time.LocalDateTime;

public class Invoice {
    private int id;
    private String name;
    private Time date;

    public Invoice(int id, String name, Time date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static String TableName() {return "invoice";}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getDate() {
        return date;
    }

    public void setDate(Time date) {
        this.date = date;
    }
}
