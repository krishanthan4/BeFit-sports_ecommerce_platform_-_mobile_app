package com.sprinsec.mobile_v2.ui.fragments.user.seller;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.SellingHistoryProductRecyclerViewAdapter;
import com.sprinsec.mobile_v2.adapter.User_home_watchlist_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.GetSellerSellingHistoryService;
import com.sprinsec.mobile_v2.data.model.SellingHistoryProductModel;
import com.sprinsec.mobile_v2.data.model.SellingHistoryProductModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.fragments.user.user_profile_fragment;
import com.sprinsec.mobile_v2.ui.fragments.user.user_watchlist_fragment;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.util.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class seller_selling_history_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__seller__selling_history, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_user_selling_history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<SellingHistoryProductModel> sellingHistoryProductList = new ArrayList<>();

        GetSellerSellingHistoryService.getData(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return; // Prevent crashes if fragment is detached

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "Selling History Data ::" + jsonObject.toString());
                    try {
                        if (jsonObject.has("selling_history_data") && jsonObject.get("selling_history_data").isJsonArray()) {
                            JsonArray products = jsonObject.getAsJsonArray("selling_history_data");

                            for (JsonElement productElement : products) {
                                JsonObject product = productElement.getAsJsonObject();
                                String id = String.valueOf(product.get("id").getAsInt());
                                String title = product.get("title").getAsString();
                                String price = String.valueOf("Rs. " + product.get("price").getAsInt());
                                String buyer = product.get("buyer").getAsString();
                                String qty = String.valueOf(product.get("qty").getAsInt());
                                String date = product.get("date").getAsString();
                                date = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date));
                                Log.i("befit_logs", "Product ID: " + id + ", Title: " + title + ", Price: " + price + ", Buyer: " + buyer + ", Quantity: " + qty + ", Date: " + date);
                                sellingHistoryProductList.add(new SellingHistoryProductModel(id, title, price, buyer, qty, date));
                            }
                        } else {
//                            redirecting to home
                            recyclerView.setVisibility(View.GONE);
                            ImageView fragment_seller_selling_history_nothing_icon = view.findViewById(R.id.fragment_seller_selling_history_nothing_icon);
                            fragment_seller_selling_history_nothing_icon.setVisibility(View.VISIBLE);
                            TextView fragment_seller_selling_history_nothing_text = view.findViewById(R.id.fragment_seller_selling_history_nothing_text);
                            fragment_seller_selling_history_nothing_text.setVisibility(View.VISIBLE);
                            Button fragment_seller_selling_history_nothing_shopping_button = view.findViewById(R.id.fragment_seller_selling_history_nothing_shopping_button);
                            fragment_seller_selling_history_nothing_shopping_button.setVisibility(View.VISIBLE);
                            fragment_seller_selling_history_nothing_shopping_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(), user_home_interface.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "user_home_fragment category error: ", e);
                    }

                    recyclerView.setAdapter(new SellingHistoryProductRecyclerViewAdapter(sellingHistoryProductList));

                });
            }

            @Override
            public void onFailure(String errorMessage) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    Log.e("befit_logs", "Error: " + errorMessage);
                    Toast.makeText(getContext(), "Something Went Wrong. Please Try Again Later", Toast.LENGTH_SHORT).show();
                });
            }
        });

        ImageView fragment_seller_selling_history_back_icon = view.findViewById(R.id.fragment_seller_selling_history_back_icon);
        fragment_seller_selling_history_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, user_profile_fragment.class, null).commit();

            }
        });
        return view;
    }
}