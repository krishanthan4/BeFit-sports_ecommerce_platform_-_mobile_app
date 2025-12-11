package com.sprinsec.mobile_v2.data.api.user.update_profile;

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

public class GetCities_ForDistrictService {
    public static void getData(String districtId, GeneralCallBack callback) {
        OkHttpClient client = HttpClientProvider.getClient();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("district_id", districtId);

        Request request = new Request.Builder()
                .url(Config.BACKEND_API_URL + "update_profile/GetCities_ForDistrictsProcess.php")
                .post(RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(jsonObject)))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
                    callback.onSuccess(jsonObject);
                } else {
                    Log.e("GetCartProductsService", "Unexpected response: " + response);
                    callback.onFailure("Unexpected response: " + response);
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                callback.onFailure("Error : " + e.getMessage());
                Log.e("GetCartProductsService", "Error: " + e.getMessage());
            }

        });
    }

}
