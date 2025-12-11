package com.sprinsec.mobile_v2.data.api.client;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.util.Config;
import com.sprinsec.mobile_v2.util.HttpClientProvider;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {
    private static final String TAG = "ApiClient";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client;
    private final Gson gson;

    public ApiClient() {
        this.client = HttpClientProvider.getClient();
        this.gson = new Gson();
    }

    public interface ApiCallback {
        void onSuccess(ApiResponse response);
        void onFailure(String error);
    }

    public void get(String endpoint, ApiCallback callback) {
        Request request = new Request.Builder()
                .url(Config.BACKEND_API_URL + endpoint)
                .get()
                .addHeader("Accept", "application/json")
                .build();

        executeRequest(request, callback);
    }

    public void post(String endpoint, JsonObject data, ApiCallback callback) {
        RequestBody body = RequestBody.create(gson.toJson(data), JSON);
        Request request = new Request.Builder()
                .url(Config.BACKEND_API_URL + endpoint)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        executeRequest(request, callback);
    }

    public void put(String endpoint, JsonObject data, ApiCallback callback) {
        RequestBody body = RequestBody.create(gson.toJson(data), JSON);
        Request request = new Request.Builder()
                .url(Config.BACKEND_API_URL + endpoint)
                .put(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        executeRequest(request, callback);
    }

    public void delete(String endpoint, ApiCallback callback) {
        Request request = new Request.Builder()
                .url(Config.BACKEND_API_URL + endpoint)
                .delete()
                .addHeader("Accept", "application/json")
                .build();

        executeRequest(request, callback);
    }

    private void executeRequest(Request request, ApiCallback callback) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Request failed: " + e.getMessage());
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String responseBody = response.body() != null ? response.body().string() : "{}";
                    ApiResponse apiResponse = gson.fromJson(responseBody, ApiResponse.class);
                    
                    if (apiResponse == null) {
                        apiResponse = new ApiResponse();
                        apiResponse.success = false;
                        apiResponse.message = "Invalid response";
                    }

                    if (response.isSuccessful() && apiResponse.success) {
                        callback.onSuccess(apiResponse);
                    } else {
                        callback.onFailure(apiResponse.message != null ? apiResponse.message : "Request failed");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Response parsing failed: " + e.getMessage());
                    callback.onFailure("Failed to parse response");
                }
            }
        });
    }
}
