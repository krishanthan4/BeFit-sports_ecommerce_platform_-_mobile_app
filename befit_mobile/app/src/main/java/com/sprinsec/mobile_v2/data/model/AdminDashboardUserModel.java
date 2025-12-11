package com.sprinsec.mobile_v2.data.model;

public class AdminDashboardUserModel {
    private String userName;
    private String userEmail;
    private String userPhone;
    private String registeredDate;
    private String userImage;
    private String userStatus;

    public AdminDashboardUserModel(String userName, String userEmail, String userPhone, String registeredDate, String userImage, String userStatus) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.registeredDate = registeredDate;
        this.userImage = userImage;
        this.userStatus = userStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
