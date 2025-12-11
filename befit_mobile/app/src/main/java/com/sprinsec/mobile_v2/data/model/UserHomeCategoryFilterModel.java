package com.sprinsec.mobile_v2.data.model;

public class UserHomeCategoryFilterModel {
    private String CategoryName;

    public UserHomeCategoryFilterModel(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
