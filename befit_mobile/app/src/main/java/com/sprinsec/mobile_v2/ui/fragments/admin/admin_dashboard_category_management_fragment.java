package com.sprinsec.mobile_v2.ui.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.admin.admin_dashboard_get_category_recycler_view_adapter;
import com.sprinsec.mobile_v2.adapter.admin.admin_dashboard_get_product_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.admin.GetAdminDashboardCategoryService;
import com.sprinsec.mobile_v2.data.api.admin.GetAdminDashboardProductsService;
import com.sprinsec.mobile_v2.data.model.AdminDashboardProductModel;
import com.sprinsec.mobile_v2.data.model.UserHomeCategoryModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.util.CommonFunctions;
import com.sprinsec.mobile_v2.util.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class admin_dashboard_category_management_fragment extends Fragment {

    ArrayList<UserHomeCategoryModel> userHomeCategoryModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__dashboard_category_management, container, false);

        userHomeCategoryModelList = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.fragment_admin_dashboard_category_management_recycler_view);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int spanCount = screenWidthDp > 600 ? 5 : 2;

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        GetAdminDashboardCategoryService.getCategory(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return; // Prevent crashes if fragment is detached

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "all_categories Data ::" + jsonObject.toString());
                    try {
                        if (jsonObject.has("all_categories") && jsonObject.get("all_categories").isJsonArray()) {
                            JsonArray categories = jsonObject.getAsJsonArray("all_categories");

                            for (JsonElement categoryElement : categories) {
                                JsonObject category = categoryElement.getAsJsonObject();
                                String id = String.valueOf(category.get("id").getAsInt());
                                String name = category.get("name").getAsString();
                                String imgPath = category.get("img").getAsString();
                                String fullImagePath = Config.BACKEND_URL + imgPath.substring(2);
                                Log.i("befit_logs", fullImagePath);
                                userHomeCategoryModelList.add(new UserHomeCategoryModel(id, name, fullImagePath));
                            }
                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "admin_dashboard_category_management category error: ", e);
                    }
                    recyclerView.setAdapter(new admin_dashboard_get_category_recycler_view_adapter(userHomeCategoryModelList));
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