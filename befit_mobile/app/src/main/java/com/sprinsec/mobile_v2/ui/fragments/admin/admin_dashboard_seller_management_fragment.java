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
import com.sprinsec.mobile_v2.adapter.admin.admin_dashboard_get_sellers_recycler_view_adapter;
import com.sprinsec.mobile_v2.adapter.admin.admin_dashboard_get_users_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.admin.GetAdminDashboardSellerService;
import com.sprinsec.mobile_v2.data.model.AdminDashboardSellerModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.util.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class admin_dashboard_seller_management_fragment extends Fragment {
    private ArrayList<AdminDashboardSellerModel> adminSellerlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__dashboard_seller_management, container, false);

        adminSellerlist = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.fragment_admin_dashboard_seller_management_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        GetAdminDashboardSellerService.getSellers(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return; // Prevent crashes if fragment is detached

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "all_sellers Data ::" + jsonObject.toString());
                    try {
                        if (jsonObject.has("all_sellers") && jsonObject.get("all_sellers").isJsonArray()) {
                            JsonArray products = jsonObject.getAsJsonArray("all_sellers");

                            for (JsonElement usersElement : products) {
                                JsonObject users = usersElement.getAsJsonObject();
                                String username = users.get("username").getAsString();
                                String email = users.get("email").getAsString();
                                String date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(users.get("joined_date").getAsString()));

                                String mobile;
                                JsonElement mobileElement = users.get("mobile");
                                if (mobileElement != null && !mobileElement.isJsonNull()) {
                                    mobile = mobileElement.getAsString();
                                } else {
                                    mobile = "";
                                }
                                String status = users.get("status").getAsString().toUpperCase(Locale.ROOT);

                                String totalProducts = String.valueOf(users.get("totalProducts").getAsInt());
                                String imgPath = users.get("profile_image").getAsString();
                                String fullImagePath = Config.BACKEND_URL + imgPath.substring(3);
                                Log.i("befit_logs", fullImagePath);
                                adminSellerlist.add(new AdminDashboardSellerModel(username, email, mobile, date, fullImagePath, totalProducts, status));
                            }
                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "user_home_fragment category error: ", e);
                    }
                    recyclerView.setAdapter(new admin_dashboard_get_sellers_recycler_view_adapter(adminSellerlist));
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