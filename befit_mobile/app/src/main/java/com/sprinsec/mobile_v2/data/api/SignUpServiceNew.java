package com.sprinsec.mobile_v2.data.api;

import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.data.api.client.ApiClient;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;

public class SignUpService {
    public static void signup(JsonObject userData, GeneralCallBack callback) {
        ApiClient apiClient = new ApiClient();
        apiClient.post("auth/register", userData, new ApiClient.ApiCallback() {
            @Override
            public void onSuccess(com.sprinsec.mobile_v2.data.api.client.ApiResponse response) {
                callback.onSuccess(response.getDataAsObject());
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }
}
