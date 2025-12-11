package com.sprinsec.mobile_v2.data.api;

import android.graphics.Bitmap;
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

public class VerifyCodeService {
    public static void Send(String verifyCode, GeneralCallBack callback) {
        OkHttpClient client = HttpClientProvider.getClient();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("verifyCode", verifyCode);
        jsonObject.addProperty("rememberMe", true);
        Request request = new Request.Builder()
                .url(Config.BACKEND_API_URL + "adminVerificationProcess.php")
                .post(RequestBody.create(new Gson().toJson(jsonObject), null))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
                    callback.onSuccess(jsonObject);
                } else {
                    Log.e("VerifyCodeService", "Unexpected response: " + response);
                    callback.onFailure("Unexpected response: " + response);
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                callback.onFailure("Error : " + e.getMessage());
                Log.e("VerifyCodeService", "Error: " + e.getMessage());
            }

        });
    }

}
