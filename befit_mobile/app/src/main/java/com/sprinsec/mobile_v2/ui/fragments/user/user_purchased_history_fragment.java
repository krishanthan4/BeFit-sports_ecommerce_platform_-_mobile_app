package com.sprinsec.mobile_v2.ui.fragments.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.sprinsec.mobile_v2.adapter.UserPurchasedHistoryRecyclerViewAdapter;
import com.sprinsec.mobile_v2.data.api.GetPurchasedHistoryService;
import com.sprinsec.mobile_v2.data.api.GetSellerMyProductsService;
import com.sprinsec.mobile_v2.data.model.GetPurchasedHistoryModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.util.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class user_purchased_history_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_purchased_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_user_purchased_history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<GetPurchasedHistoryModel> purchased_history_list = new ArrayList<>();
        GetPurchasedHistoryService.getData(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "User Purchased History Data ::" + jsonObject.toString());
                    try {
                        if (jsonObject.has("purchased_history_data") && jsonObject.get("purchased_history_data").isJsonArray()) {
                            JsonArray products = jsonObject.getAsJsonArray("purchased_history_data");

                            for (JsonElement productElement : products) {
                                JsonObject product = productElement.getAsJsonObject();
                                String order_id = product.get("order_id").getAsString();
                                String product_id = product.get("product_id").getAsString();
                                String product_name = product.get("product_name").getAsString();
                                String product_price = String.valueOf("Rs. " + product.get("product_price").getAsInt());
                                String order_total = String.valueOf("Total: Rs. " + product.get("order_total").getAsInt());
                                String date = product.get("date").getAsString();
                                date = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date));
                                String order_status = product.get("order_status").getAsString().toLowerCase();
                                String imgPath = product.get("img_path").getAsString();
                                String fullImagePath = "";
                                if (imgPath.length() > 5) {
                                    fullImagePath = Config.BACKEND_URL + imgPath.substring(3);
                                }
                                purchased_history_list.add(new GetPurchasedHistoryModel(order_id, product_id, date, order_status, order_total, product_name, product_price, fullImagePath));
                            }
                        } else {
//                            redirecting to home
                            recyclerView.setVisibility(View.GONE);
                            ImageView fragment_user_purchased_history_nothing_icon = view.findViewById(R.id.fragment_user_purchased_history_nothing_icon);
                            fragment_user_purchased_history_nothing_icon.setVisibility(View.VISIBLE);
                            TextView fragment_user_purchased_history_nothing_text = view.findViewById(R.id.fragment_user_purchased_history_nothing_text);
                            fragment_user_purchased_history_nothing_text.setVisibility(View.VISIBLE);
                            Button fragment_user_purchased_history_nothing_shopping_button = view.findViewById(R.id.fragment_user_purchased_history_nothing_shopping_button);
                            fragment_user_purchased_history_nothing_shopping_button.setVisibility(View.VISIBLE);
                            fragment_user_purchased_history_nothing_shopping_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), user_home_interface.class);
                                startActivity(intent);

                                }
                            });

                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "user_purchased_history_activity error: ", e);
                    }
                    FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    recyclerView.setAdapter(new UserPurchasedHistoryRecyclerViewAdapter(purchased_history_list, fragmentTransaction));

                });
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });


        ImageView fragment_user_purchased_history_back_icon = view.findViewById(R.id.fragment_user_purchased_history_back_icon);
        fragment_user_purchased_history_back_icon.setOnClickListener(new View.OnClickListener() {
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