package com.sprinsec.mobile_v2.adapter.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.AdminDashboardFeedbackModel;

import java.util.ArrayList;

public class admin_dashboard_get_feedback_recycler_view_adapter extends RecyclerView.Adapter<admin_dashboard_get_feedback_recycler_view_adapter.MyViewHolder> {
    private ArrayList<AdminDashboardFeedbackModel> adminDashboardFeedbackModelArrayList;

    public admin_dashboard_get_feedback_recycler_view_adapter(ArrayList<AdminDashboardFeedbackModel> adminDashboardFeedbackModelArrayList) {
        this.adminDashboardFeedbackModelArrayList = adminDashboardFeedbackModelArrayList;
    }

    @NonNull
    @Override
    public admin_dashboard_get_feedback_recycler_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_admin_dashboard_review_management_item, parent, false);
        return new admin_dashboard_get_feedback_recycler_view_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_dashboard_get_feedback_recycler_view_adapter.MyViewHolder holder, int position) {
        AdminDashboardFeedbackModel feedback = adminDashboardFeedbackModelArrayList.get(position);
        holder.feedbackId.setText(feedback.getFeedbackId());
        holder.reviewerName.setText(feedback.getFeedbackTitle());
        holder.review.setText(feedback.getFeedbackDescription());
        holder.feedbackRating.setRating(feedback.getFeedbackRating());
        holder.feedbackDate.setText(feedback.getFeedbackDate());
        holder.productName.setText(feedback.getProductName());
    }

    @Override
    public int getItemCount() {
        return adminDashboardFeedbackModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView feedbackId, reviewerName, review, feedbackDate, productName;
        RatingBar feedbackRating;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            feedbackId = itemView.findViewById(R.id.single_admin_dashboard_review_management_review_id);
            reviewerName = itemView.findViewById(R.id.single_admin_dashboard_review_management_reviewer_name);
            review = itemView.findViewById(R.id.single_admin_dashboard_review_management_review);
            feedbackRating = itemView.findViewById(R.id.single_admin_dashboard_review_management_ratings);
            feedbackDate = itemView.findViewById(R.id.single_admin_dashboard_review_management_date);
            productName = itemView.findViewById(R.id.single_admin_dashboard_review_management_product_name);

        }
    }
}
