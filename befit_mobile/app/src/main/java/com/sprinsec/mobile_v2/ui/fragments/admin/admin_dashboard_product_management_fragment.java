package com.sprinsec.mobile_v2.ui.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.admin.admin_dashboard_get_product_recycler_view_adapter;
import com.sprinsec.mobile_v2.adapter.admin.admin_dashboard_get_sellers_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.admin.GetAdminDashboardProductsService;
import com.sprinsec.mobile_v2.data.model.AdminDashboardProductModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.util.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class admin_dashboard_product_management_fragment extends Fragment {
    ArrayList<AdminDashboardProductModel> adminProductlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__dashboard_product_management, container, false);

        adminProductlist = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.fragment_admin_dashboard_product_management_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        GetAdminDashboardProductsService.getProducts(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return; // Prevent crashes if fragment is detached

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "all_products Data ::" + jsonObject.toString());
                    try {
                        if (jsonObject.has("all_products") && jsonObject.get("all_products").isJsonArray()) {
                            JsonArray products = jsonObject.getAsJsonArray("all_products");

                            for (JsonElement usersElement : products) {
                                JsonObject users = usersElement.getAsJsonObject();
                                String id = String.valueOf(users.get("id").getAsInt());
                                String name = users.get("title").getAsString();
                                String price = users.get("price").getAsString();
                                String date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(users.get("datetime_added").getAsString()));

                                String status = users.get("status").getAsString().toUpperCase(Locale.ROOT);

                                String qty = String.valueOf(users.get("qty").getAsInt());
                                String imgPath = users.get("product_image").getAsString();
                                String fullImagePath = Config.BACKEND_URL + imgPath.substring(2);
                                Log.i("befit_logs", fullImagePath);
                                adminProductlist.add(new AdminDashboardProductModel(id, name, price, qty, fullImagePath, status, date));
                            }
                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "user_home_fragment category error: ", e);
                    }
                    recyclerView.setAdapter(new admin_dashboard_get_product_recycler_view_adapter(adminProductlist));
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
        return view;
    }
}