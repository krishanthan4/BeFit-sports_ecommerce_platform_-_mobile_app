package com.sprinsec.mobile_v2;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import static org.junit.Assert.*;

import com.sprinsec.mobile_v2.util.PaymentsUtil;

public class PaymentsUtilTest {

    @Test
    public void testInitiatePayment() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = PaymentsUtil.initiatePayment(
                context,
                "order123",
                100.0f,
                "USD",
                "Test Item",
                "John",
                "Doe",
                "john.doe@example.com",
                "1234567890",
                "123 Street",
                "City",
                "Country"
        );

        assertNotNull(intent);
        assertEquals("lk.payhere.androidsdk.PHMainActivity", intent.getComponent().getClassName());
    }
}