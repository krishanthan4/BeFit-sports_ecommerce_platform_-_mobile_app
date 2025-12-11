package com.sprinsec.mobile_v2.data.api;

import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.data.api.client.ApiClient;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;

public class SigninService {
    public static void signin(String email, String password, GeneralCallBack callback) {
        ApiClient apiClient = new ApiClient();
        JsonObject data = new JsonObject();
        data.addProperty("email", email);
        data.addProperty("password", password);
        data.addProperty("rememberMe", true);

        apiClient.post("auth/login", data, new ApiClient.ApiCallback() {
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
