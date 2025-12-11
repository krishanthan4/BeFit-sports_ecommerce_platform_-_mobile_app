package com.sprinsec.mobile_v2.data.model;

public class UserOrderDetailsModel {
    private String productName;
    private String productPrice;
    private String productDetails;
    private String productImage;

    public UserOrderDetailsModel(String productName, String productPrice, String productDetails, String productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDetails = productDetails;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
