package com.sprinsec.mobile_v2.ui.fragments.user.seller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.SellerOrderStatusRecyclerViewAdapter;
import com.sprinsec.mobile_v2.adapter.SellingHistoryProductRecyclerViewAdapter;
import com.sprinsec.mobile_v2.data.api.GetSellerOrderStatusService;
import com.sprinsec.mobile_v2.data.model.SellerOrderStatusModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class seller_order_status_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seller_order_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_seller_order_status), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.fragment_seller_order_status_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SellerOrderStatusModel> sellerOrderStatusList = new ArrayList<>();
        GetSellerOrderStatusService.getData(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {

                runOnUiThread(() -> {
                    Log.i("befit_logs", "Order Status Data ::" + jsonObject.toString());
                    try {
                        if (jsonObject.has("order_status_data") && jsonObject.get("order_status_data").isJsonArray()) {
                            JsonArray products = jsonObject.getAsJsonArray("order_status_data");

                            for (JsonElement productElement : products) {
                                JsonObject product = productElement.getAsJsonObject();
                                String id = product.get("order_id").getAsString();
                                String product1 = product.get("product").getAsString();
                                String price = String.valueOf("Rs. " + product.get("price").getAsInt());
                                String date = product.get("date").getAsString();
                                date = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date));
                                String order_status = product.get("order_status").getAsString();

                                sellerOrderStatusList.add(new SellerOrderStatusModel(id, order_status, date, price, product1));
                            }
                        } else {
//                            redirecting to home
                            recyclerView.setVisibility(View.GONE);
                            ImageView fragment_seller_order_status_nothing_icon = findViewById(R.id.fragment_seller_order_status_nothing_icon);
                            fragment_seller_order_status_nothing_icon.setVisibility(View.VISIBLE);
                            TextView fragment_seller_order_status_nothing_text = findViewById(R.id.fragment_seller_order_status_nothing_text);
                            fragment_seller_order_status_nothing_text.setVisibility(View.VISIBLE);
                            Button fragment_seller_order_status_nothing_shopping_button = findViewById(R.id.fragment_seller_order_status_nothing_shopping_button);
                            fragment_seller_order_status_nothing_shopping_button.setVisibility(View.VISIBLE);
                            fragment_seller_order_status_nothing_shopping_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(seller_order_status_activity.this, user_home_interface.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "seller_order_status_activity error: ", e);
                    }

                    recyclerView.setAdapter(new SellerOrderStatusRecyclerViewAdapter(sellerOrderStatusList));

                });
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

        ImageView fragment_seller_order_status_back_icon = findViewById(R.id.fragment_seller_order_status_back_icon);
        fragment_seller_order_status_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(seller_order_status_activity.this, user_home_interface.class);
                startActivity(intent);
            }
        });
    }
}