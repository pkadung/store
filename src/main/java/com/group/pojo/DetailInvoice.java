package com.group.pojo;

public class DetailInvoice {
    private int id;
    private int invoiceId;
    private int productId;
    private int amount;
    private double price;

    public DetailInvoice(int id, int invoiceId, int productId, int amount, double price) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.amount = amount;
        this.price = price;
    }

    public static String TableName() {return "detail_invoice";}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
