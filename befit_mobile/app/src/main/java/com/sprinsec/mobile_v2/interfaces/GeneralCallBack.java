package com.sprinsec.mobile_v2.interfaces;

import com.google.gson.JsonObject;

public interface GeneralCallBack {
    void onSuccess(JsonObject response);

    void onFailure(String errorMessage);
}
