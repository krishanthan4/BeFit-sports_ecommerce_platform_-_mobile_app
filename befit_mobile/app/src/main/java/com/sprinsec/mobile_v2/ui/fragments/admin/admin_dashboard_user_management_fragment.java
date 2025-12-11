package com.sprinsec.mobile_v2.ui.fragments.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.sprinsec.mobile_v2.adapter.User_home_watchlist_recycler_view_adapter;
import com.sprinsec.mobile_v2.adapter.admin.admin_dashboard_get_users_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.admin.GetAdminDashboardUserService;
import com.sprinsec.mobile_v2.data.model.AdminDashboardUserModel;
import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.fragments.user.user_watchlist_fragment;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.util.Config;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class admin_dashboard_user_management_fragment extends Fragment {
    private ArrayList<AdminDashboardUserModel> adminUserslist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__dashboard_user_management, container, false);

        adminUserslist = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.fragment_admin_dashboard_user_management_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        GetAdminDashboardUserService.getUsers(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return; // Prevent crashes if fragment is detached

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "all_users Data ::" + jsonObject.toString());
                    try {
                        if (jsonObject.has("all_users") && jsonObject.get("all_users").isJsonArray()) {
                            JsonArray products = jsonObject.getAsJsonArray("all_users");

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
                                String imgPath = users.get("profile_image").getAsString();
                                String fullImagePath = Config.BACKEND_URL + imgPath.substring(3);
                                Log.i("befit_logs", fullImagePath);
                                adminUserslist.add(new AdminDashboardUserModel(username, email, mobile, date, fullImagePath, status));
                            }
                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "user_home_fragment category error: ", e);
                    }
                    recyclerView.setAdapter(new admin_dashboard_get_users_recycler_view_adapter(adminUserslist));
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