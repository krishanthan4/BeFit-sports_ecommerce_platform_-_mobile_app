package com.sprinsec.mobile_v2;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import okhttp3.OkHttpClient;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.sprinsec.mobile_v2.util.HttpClientProvider;

public class HttpClientProviderTest {

    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        HttpClientProvider.initialize(context);
    }

    @Test
    public void testHttpClientInitialization() {
        OkHttpClient client = HttpClientProvider.getClient();
        assertNotNull(client);
    }

//    @Test(expected = AssertionError.class)
//    public void testHttpClientNotInitialized() {
//        HttpClientProvider.clearCookies();
//        HttpClientProvider.getClient();
//    }

    @Test
    public void testClearCookies() {
        HttpClientProvider.clearCookies();
        // Assuming logCookies method is public for testing purposes
        // HttpClientProvider.logCookies("Test clear cookies");
    }
}