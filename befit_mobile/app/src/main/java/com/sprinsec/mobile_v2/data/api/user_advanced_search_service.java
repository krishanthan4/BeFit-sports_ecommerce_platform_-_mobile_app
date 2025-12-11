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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class user_advanced_search_service {
    public static void Send(String search_query, String price_from, String price_to, String condition, GeneralCallBack callback) {
        OkHttpClient client = HttpClientProvider.getClient();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        JsonObject jsonObject = new JsonObject();
        if (search_query != null) {
            jsonObject.addProperty("search_query", search_query);
        }
        if (price_from != null) {
            jsonObject.addProperty("startPrice", price_from);
        }
        if (price_to != null) {
            jsonObject.addProperty("endPrice", price_to);
        }
        if (condition != null) {
            jsonObject.addProperty("condition", condition);
        }
        String json = new Gson().toJson(jsonObject);
        Log.d("user_advanced_search_service", "Request: " + json);
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(Config.BACKEND_API_URL + "filterProcess.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
                    callback.onSuccess(jsonObject);
                } else {
                    Log.e("SetToWatchlistService", "Unexpected response: " + response);
                    callback.onFailure("Unexpected response: " + response);
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                callback.onFailure("Error : " + e.getMessage());
                Log.e("SetToWatchlistService", "Error: " + e.getMessage());
            }

        });
    }

}
