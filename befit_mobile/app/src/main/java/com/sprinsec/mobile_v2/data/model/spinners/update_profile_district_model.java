package com.sprinsec.mobile_v2.data.model.spinners;

public class update_profile_district_model {
    private String district_id;
    private String district_name;

    public update_profile_district_model(String district_id, String district_name) {
        this.district_id = district_id;
        this.district_name = district_name;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }
}
