package com.sprinsec.mobile_v2.data.model;

public class AdminDashboardProductModel {
    private String productId;
    private String productName;
    private String productPrice;
    private String productQuantity;
    private String productImage;
    private String productStatus;
    private String productregistered_date;

    public AdminDashboardProductModel(String productId, String productName, String productPrice, String productQuantity, String productImage, String productStatus, String productregistered_date) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
        this.productStatus = productStatus;
        this.productregistered_date = productregistered_date;
    }

    public AdminDashboardProductModel(String productId, String productName, String productPrice, String productQuantity, String productImage, String productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
        this.productStatus = productStatus;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductregistered_date() {
        return productregistered_date;
    }

    public void setProductregistered_date(String productregistered_date) {
        this.productregistered_date = productregistered_date;
    }
}
