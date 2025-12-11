package com.sprinsec.mobile_v2.data.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.util.Config;
import com.sprinsec.mobile_v2.util.HttpClientProvider;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetSingleProductViewService {
    public static void getData(GeneralCallBack callback, String productId) {
        OkHttpClient client = HttpClientProvider.getClient();
        JsonObject productIdObject = new JsonObject();
        productIdObject.addProperty("product_id", productId);
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url(Config.BACKEND_API_URL + "singleProductGetExtraDataProcess.php")
                .post(RequestBody.create(gson.toJson(productIdObject), okhttp3.MediaType.parse("application/json; charset=utf-8")))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
                    callback.onSuccess(jsonObject);
                    Log.d("GetSingleProductService", "Response: " + responseBody);
                } else {
                    Log.e("GetSingleProductService", "Unexpected response: " + response);
                    callback.onFailure("Unexpected response: " + response);
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                callback.onFailure("Error : " + e.getMessage());
                Log.e("GetSingleProductService", "Error: " + e.getMessage());
            }

        });
    }

}
