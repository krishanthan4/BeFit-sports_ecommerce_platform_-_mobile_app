package com.sprinsec.mobile_v2.data.model;

public class SelectColorModel {
    private String colorName;
    private String colorId;

    public SelectColorModel(String colorId, String colorName) {
        this.colorName = colorName;
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
}
