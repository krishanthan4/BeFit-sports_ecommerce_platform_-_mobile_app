package com.sprinsec.mobile_v2.data.model;

public class AdminDashboardSellerModel {
    private String sellerName;
    private String sellerEmail;
    private String sellerPhone;
    private String registeredDate;
    private String sellerImage;
    private String sellerTotalProducts;
    private String sellerStatus;

    public AdminDashboardSellerModel(String sellerName, String sellerEmail, String sellerPhone, String registeredDate, String sellerImage, String sellerTotalProducts, String sellerStatus) {
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
        this.sellerPhone = sellerPhone;
        this.registeredDate = registeredDate;
        this.sellerImage = sellerImage;
        this.sellerTotalProducts = sellerTotalProducts;
        this.sellerStatus = sellerStatus;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getSellerImage() {
        return sellerImage;
    }

    public void setSellerImage(String sellerImage) {
        this.sellerImage = sellerImage;
    }

    public String getSellerTotalProducts() {
        return sellerTotalProducts;
    }

    public void setSellerTotalProducts(String sellerTotalProducts) {
        this.sellerTotalProducts = sellerTotalProducts;
    }

    public String getSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(String sellerStatus) {
        this.sellerStatus = sellerStatus;
    }
}
