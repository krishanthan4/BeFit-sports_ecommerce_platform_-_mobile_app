package com.sprinsec.mobile_v2.data.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.util.Config;
import com.sprinsec.mobile_v2.util.HttpClientProvider;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SellerAddProductService {
    public static void setData(GeneralCallBack callback,
                               String categoryid,
                               String brandid,
                               String title,
                               String modelid,
                               String condition,
                               String colorId,
                               String quantity,
                               String cost_per_item,
                               String delivery_fee,
                               ArrayList<byte[]> images,
                               String description,
                               Context context) {

        OkHttpClient client = HttpClientProvider.getClient();

        MultipartBody.Builder formBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("category", categoryid)
                .addFormDataPart("brand", brandid)
                .addFormDataPart("title", title)
                .addFormDataPart("model", modelid)
                .addFormDataPart("condition", condition)
                .addFormDataPart("color", colorId)
                .addFormDataPart("quantity", quantity)
                .addFormDataPart("description", description)
                .addFormDataPart("cost", cost_per_item)
                .addFormDataPart("deliveryFee", delivery_fee);

        // Add images to the form data
        for (int i = 0; i < images.size(); i++) {
            byte[] imageBytes = images.get(i);
            if (imageBytes != null && imageBytes.length > 0) {
                RequestBody imageBody = RequestBody.create(
                        MediaType.parse("image/jpeg"), // You can adjust the media type if needed
                        imageBytes
                );
                formBodyBuilder.addFormDataPart(
                        "image" + i, // This matches your PHP backend's expectation
                        "image" + i + ".jpg", // Filename
                        imageBody
                );
            }
        }

        Request request = new Request.Builder()
                .url(Config.BACKEND_API_URL + "addProductProcess.php")
                .post(formBodyBuilder.build())
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
                    callback.onSuccess(jsonObject);
                } else {
                    String errorMessage = response.body() != null ? response.body().string() : "Unknown error";
                    Log.e("AddProductService", "Unexpected response: " + errorMessage);
                    callback.onFailure("Unexpected response: " + errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("AddProductService", "Error: " + e.getMessage());
                callback.onFailure("Error: " + e.getMessage());
            }
        });
    }
}