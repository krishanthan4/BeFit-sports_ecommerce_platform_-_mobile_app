package com.sprinsec.mobile_v2.data.api;

import android.content.Context;
import android.media.MediaSession2;
import android.util.Log;

import androidx.annotation.NonNull;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.util.Config;
import com.sprinsec.mobile_v2.util.HttpClientProvider;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CheckUserSessionService {
    private static final OkHttpClient client = HttpClientProvider.getClient();

    public static void checkUserSession(SessionCallback callback) {
        Request request = new Request.Builder()
                .url(Config.BACKEND_API_URL + "checkUserSessionProcess.php")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("befit_logs", "CheckUserSessionService :: Error " + e.getMessage());
                callback.onSessionChecked(false); // Assume no session on error
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    ResponseBody responseBody = response.body();
                    if (responseBody == null) {
                        Log.e("befit_logs", "CheckUserSessionService :: Empty response");
                        callback.onSessionChecked(false);
                        return;
                    }

                    String bodyString = responseBody.string();
                    JsonObject jsonObject = new Gson().fromJson(bodyString, JsonObject.class);

                    boolean isUserSession = jsonObject != null &&
                            jsonObject.has("message") &&
                            "already".equals(jsonObject.get("message").getAsString());

                    Log.i("befit_logs", "CheckUserSessionService :: Response " + bodyString);
                    callback.onSessionChecked(isUserSession);
                } catch (Exception e) {
                    Log.e("befit_logs", "CheckUserSessionService :: JSON Parsing Error " + e.getMessage());
                    callback.onSessionChecked(false);
                }
            }
        });
    }

    public interface SessionCallback {
        void onSessionChecked(boolean isActive);
    }
}
