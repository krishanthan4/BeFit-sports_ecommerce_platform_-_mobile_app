package com.sprinsec.mobile_v2.ui.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.sprinsec.mobile_v2.adapter.admin.admin_dashboard_get_feedback_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.admin.GetAdminDashboardCategoryService;
import com.sprinsec.mobile_v2.data.api.admin.GetAdminDashboardFeedbackService;
import com.sprinsec.mobile_v2.data.model.AdminDashboardFeedbackModel;
import com.sprinsec.mobile_v2.data.model.UserHomeCategoryModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.util.Config;

import java.util.ArrayList;

public class admin_dashboard_feedback_and_ratings_management_fragment extends Fragment {

    ArrayList<AdminDashboardFeedbackModel> adminDashboardFeedbackModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__dashboard_feedback_and_ratings_management, container, false);


        adminDashboardFeedbackModelList = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.fragment_admin_dashboard_feedback_and_ratings_management_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        GetAdminDashboardFeedbackService.getFeedback(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return; // Prevent crashes if fragment is detached

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "all_feedbacks Data ::" + jsonObject.toString());
                    try {
                        if (jsonObject.has("all_feedbacks") && jsonObject.get("all_feedbacks").isJsonArray()) {
                            JsonArray feedbacks = jsonObject.getAsJsonArray("all_feedbacks");

                            for (JsonElement feedbackElement : feedbacks) {
                                JsonObject feedback = feedbackElement.getAsJsonObject();
                                String feedbackId = String.valueOf(feedback.get("id").getAsInt());
                                String ReviewerName = feedback.get("reviewerName").getAsString();
                                String feedbackDescription = feedback.get("review").getAsString();
                                int feedbackRating = feedback.get("feedbackRating").getAsInt();
                                String feedbackDate = feedback.get("date").getAsString();
                                String productName = feedback.get("product_name").getAsString();
                                adminDashboardFeedbackModelList.add(new AdminDashboardFeedbackModel(feedbackId, ReviewerName, feedbackDescription, feedbackRating, feedbackDate, productName));
                            }
                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "admin_dashboard_feedback_management feedback error: ", e);
                    }
                    recyclerView.setAdapter(new admin_dashboard_get_feedback_recycler_view_adapter(adminDashboardFeedbackModelList));
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