package com.sprinsec.mobile_v2.data.model;

public class SellerOrderStatusModel {
    private String order_id;
    private String order_status;
    private String order_date;
    private String order_total_price;
    private String products;

    public SellerOrderStatusModel(String order_id, String order_status, String order_date, String order_total_price, String products) {
        this.order_id = order_id;
        this.order_status = order_status;
        this.order_date = order_date;
        this.order_total_price = order_total_price;
        this.products = products;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_total_price() {
        return order_total_price;
    }

    public void setOrder_total_price(String order_total_price) {
        this.order_total_price = order_total_price;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }
}
