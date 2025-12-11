package com.sprinsec.mobile_v2.ui.fragments.user.seller;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.SellerMyProductItemRecyclerViewAdapter;
import com.sprinsec.mobile_v2.adapter.SellerOrderStatusRecyclerViewAdapter;
import com.sprinsec.mobile_v2.data.api.GetSellerMyProductsService;
import com.sprinsec.mobile_v2.data.model.SellerOrderStatusModel;
import com.sprinsec.mobile_v2.data.model.SellerMyProductItemModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.fragments.user.user_profile_fragment;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.util.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class seller_my_products_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__seller__my_products, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_seller_my_products_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<SellerMyProductItemModel> sellerMyProductsList = new ArrayList<>();
        GetSellerMyProductsService.getData(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "Seller My Products Data ::" + jsonObject.toString());
                    try {
                        if (jsonObject.has("my_products_data") && jsonObject.get("my_products_data").isJsonArray()) {
                            JsonArray products = jsonObject.getAsJsonArray("my_products_data");

                            for (JsonElement productElement : products) {
                                JsonObject product = productElement.getAsJsonObject();
                                String id = String.valueOf(product.get("id").getAsInt());
                                String title = product.get("title").getAsString();
                                String price = String.valueOf("Rs. " + product.get("price").getAsInt());
                                String imgPath = product.get("img_path").getAsString();
                                String qty = String.valueOf(product.get("qty").getAsInt());
                                String status = product.get("status").getAsString().toLowerCase();
                                String fullImagePath = Config.BACKEND_URL + imgPath.substring(3);
                                Log.i("befit_logs", fullImagePath);
                                sellerMyProductsList.add(new SellerMyProductItemModel(title, fullImagePath, price, qty, status));
                            }
                        } else {
//                            redirecting to home
                            recyclerView.setVisibility(View.GONE);
                            ImageView fragment_seller_my_products_nothing_icon = view.findViewById(R.id.fragment_seller_my_products_nothing_icon);
                            fragment_seller_my_products_nothing_icon.setVisibility(View.VISIBLE);
                            TextView fragment_seller_my_products_nothing_text = view.findViewById(R.id.fragment_seller_my_products_nothing_text);
                            fragment_seller_my_products_nothing_text.setVisibility(View.VISIBLE);
                            Button fragment_seller_my_products_nothing_shopping_button = view.findViewById(R.id.fragment_seller_my_products_nothing_shopping_button);
                            fragment_seller_my_products_nothing_shopping_button.setVisibility(View.VISIBLE);
                            fragment_seller_my_products_nothing_shopping_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, seller_add_products_fragment.class, null).commit();

                                }
                            });

                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "seller_my_products_activity error: ", e);
                    }

                    recyclerView.setAdapter(new SellerMyProductItemRecyclerViewAdapter(sellerMyProductsList));

                });
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });


        ImageView fragment_seller_my_products_back_icon = view.findViewById(R.id.fragment_seller_my_products_back_icon);
        fragment_seller_my_products_back_icon.setOnClickListener(new View.OnClickListener() {
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