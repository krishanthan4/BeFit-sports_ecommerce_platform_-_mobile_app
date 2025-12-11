package com.sprinsec.mobile_v2.data.api;

import com.sprinsec.mobile_v2.data.api.client.ApiClient;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;

public class GetWatchlistService {
    public static void getWatchlist(GeneralCallBack callback) {
        ApiClient apiClient = new ApiClient();
        apiClient.get("wishlist", new ApiClient.ApiCallback() {
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
