package com.sprinsec.mobile_v2.data.api;

import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.data.api.client.ApiClient;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;

public class AddToCartService {
    public static void Send(String product_id, String qty, GeneralCallBack callback) {
        ApiClient apiClient = new ApiClient();
        JsonObject data = new JsonObject();
        data.addProperty("product_id", product_id);
        data.addProperty("quantity", qty);

        apiClient.post("cart", data, new ApiClient.ApiCallback() {
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
