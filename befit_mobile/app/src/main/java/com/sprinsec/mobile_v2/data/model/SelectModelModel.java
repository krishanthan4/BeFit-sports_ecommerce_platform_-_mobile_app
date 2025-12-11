package com.sprinsec.mobile_v2.data.model;

public class SelectModelModel {
    private String model_id;
    private String model_name;

    public SelectModelModel(String model_id, String model_name) {
        this.model_id = model_id;
        this.model_name = model_name;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }
}
