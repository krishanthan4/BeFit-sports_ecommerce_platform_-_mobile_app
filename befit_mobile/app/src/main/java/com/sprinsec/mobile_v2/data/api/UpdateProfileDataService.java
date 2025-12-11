package com.sprinsec.mobile_v2.data.api;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.util.Config;
import com.sprinsec.mobile_v2.util.HttpClientProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateProfileDataService {
    public static void Send(String selectedGender, String firstName, String lastName, String mobile, String addressLine1, String addressLine2, String province, String district, String city, String country, byte[] selectedImageUri, GeneralCallBack callback) {

        OkHttpClient client = HttpClientProvider.getClient();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        builder.addFormDataPart("firstName", firstName)
                .addFormDataPart("firstName", firstName)
                .addFormDataPart("lastName", lastName)
                .addFormDataPart("mobile", mobile)
                .addFormDataPart("gender", selectedGender)
                .addFormDataPart("line1", addressLine1)
                .addFormDataPart("line2", addressLine2)
                .addFormDataPart("province", province)
                .addFormDataPart("district", district)
                .addFormDataPart("city", city)
                .addFormDataPart("country", country);
        if (selectedImageUri != null) {
            builder.addFormDataPart("profile_image", "avatar.jpg", RequestBody.create(selectedImageUri, MediaType.parse("image/jpeg")));
        }
        MultipartBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(Config.BACKEND_API_URL + "updateProfileProcess.php")
                .post(requestBody)
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
