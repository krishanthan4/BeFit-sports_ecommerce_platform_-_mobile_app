package com.sprinsec.mobile_v2.util;

import android.content.Context;
import android.content.Intent;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;

public class PaymentsUtil {

    public static Intent initiatePayment(Context context, String orderId, float amount, String currency, String itemsNames, String customerFirstName, String customerLastName, String customerEmail, String phone, String address, String city, String country) {
        InitRequest req = new InitRequest();
        req.setMerchantId(Config.PAYHERE_MERCHANT_ID);       // Merchant ID
        req.setCurrency(currency);             // Currency code LKR/USD/GBP/EUR/AUD
        req.setAmount(amount);             // Final Amount to be charged
        req.setOrderId(orderId);        // Unique Reference ID
        req.setItemsDescription(itemsNames);  // Item description title
//        req.setCustom1("This is the custom message 1");
//        req.setCustom2("This is the custom message 2");
        req.getCustomer().setFirstName(customerFirstName);
        req.getCustomer().setLastName(customerLastName);
        req.getCustomer().setEmail(customerEmail);
        req.getCustomer().setPhone(phone);
        req.getCustomer().getAddress().setAddress(address);
        req.getCustomer().getAddress().setCity(city);
        req.getCustomer().getAddress().setCountry(country);
//                req.setNotifyUrl(“xxxx”);           // Notifiy Url
//        req.getCustomer().getDeliveryAddress().setAddress("No.2, Kandy Road");
//        req.getCustomer().getDeliveryAddress().setCity("Kadawatha");
//        req.getCustomer().getDeliveryAddress().setCountry("Sri Lanka");
//        req.getItems().add(new Item(null, "Door bell wireless", 1, 1000.0));

        Intent intent = new Intent(context, PHMainActivity.class);
        intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
        PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
        return intent;
//        context.startActivityForResult(intent, PAYHERE_REQUEST);
    }
}
