package com.sprinsec.mobile_v2.data.model;

public class SellingHistoryProductModel {
    private String productId;
    private String productName;
    private String productPrice;
    private String buyerName;
    private String boughtQuantity;
    private String selledDate;

    public SellingHistoryProductModel(String productId, String productName, String productPrice, String buyerName, String boughtQuantity, String selledDate) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.buyerName = buyerName;
        this.boughtQuantity = boughtQuantity;
        this.selledDate = selledDate;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBoughtQuantity() {
        return boughtQuantity;
    }

    public void setBoughtQuantity(String boughtQuantity) {
        this.boughtQuantity = boughtQuantity;
    }

    public String getSelledDate() {
        return selledDate;
    }

    public void setSelledDate(String selledDate) {
        this.selledDate = selledDate;
    }
}
