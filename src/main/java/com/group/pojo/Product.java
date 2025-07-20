package com.group.pojo;

public class Product {
    private int id;
    private String name;
    private int category_id;
    private int amount;
    private double price;

    public Product(int id, String name, int category_id, int amount, double price) {
        this.id = id;
        this.name = name;
        this.category_id = category_id;
        this.amount = amount;
        this.price = price;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static String TableName() {return "product";}

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

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
