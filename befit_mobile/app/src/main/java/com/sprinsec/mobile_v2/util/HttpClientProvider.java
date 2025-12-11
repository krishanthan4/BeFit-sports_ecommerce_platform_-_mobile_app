package com.sprinsec.mobile_v2.util;

import android.content.Context;
import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import okhttp3.Cookie;
import okhttp3.OkHttpClient;

import java.util.List;

public class HttpClientProvider {
    private static OkHttpClient client;
    private static ClearableCookieJar cookieJar;
    private static SharedPrefsCookiePersistor cookiePersistor;

    public static void initialize(Context context) {
        cookiePersistor = new SharedPrefsCookiePersistor(context);
        cookieJar = new PersistentCookieJar(new SetCookieCache(), cookiePersistor);

        client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

//        logCookies("HttpClient initialized");
    }

    public static OkHttpClient getClient() {
        if (client == null) {
            throw new IllegalStateException("HttpClientProvider is not initialized. Call initialize(context) first.");
        }
//        logCookies("HttpClient accessed");
        return client;
    }

    private static void logCookies(String tag) {
        if (cookiePersistor == null) {
            Log.i("CookieLog", tag + ": CookiePersistor is NULL");
            return;
        }

        List<Cookie> cookies = cookiePersistor.loadAll();
        if (cookies.isEmpty()) {
            Log.i("CookieLog", tag + ": No cookies stored");
        } else {
            for (Cookie cookie : cookies) {
                Log.i("CookieLog", tag + ": Stored Cookie - " + cookie.name() + " = " + cookie.value());
            }
        }
    }

    public static void clearCookies() {
        if (cookieJar != null) {
            cookieJar.clear();
            Log.i("CookieLog", "All cookies cleared");
        }
    }
}

